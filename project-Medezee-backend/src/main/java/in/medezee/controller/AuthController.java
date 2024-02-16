package in.medezee.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.medezee.config.JwtProvider;
import in.medezee.exception.UserException;
import in.medezee.modal.User;
import in.medezee.repository.UserRepository;
import in.medezee.request.LoginRequest;
import in.medezee.response.AuthResponse;
import in.medezee.service.CustomeUserServiceImplementation;

@RestController
@RequestMapping("/auth")
public class AuthController{
	
	private UserRepository userRepository;
	
	private JwtProvider jwtProvider;
	
	private PasswordEncoder passwordEncoder;
	
	private CustomeUserServiceImplementation customeUserService;
	
	public AuthController(UserRepository userRepository,CustomeUserServiceImplementation customeUserService,PasswordEncoder passwordEncoder,JwtProvider jwtProvider) {
		this.userRepository=userRepository;
		this.customeUserService=customeUserService;
		this.passwordEncoder= passwordEncoder;
		this.jwtProvider=jwtProvider;
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> createUserHandler(@RequestBody User user) throws UserException{
		System.out.println("Hello**********************");
		String email=user.getEmail();
		String password=user.getPassword();
		String firstName=user.getFirstName();
		String lastName=user.getLastName();
		
		User isEmailExist=userRepository.findByEmail(email);
		if(isEmailExist!=null) {
			throw new UserException("email already linked with another account");
		}
		
		User createdUser=new User();
		createdUser.setEmail(email);
		createdUser.setFirstName(firstName);
		createdUser.setLastName(lastName);
		createdUser.setPassword(passwordEncoder.encode(password));
		
		User savedUser=userRepository.save(createdUser);
		
		Authentication authentication=new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token=jwtProvider.generateToken(authentication);
		AuthResponse authResponse=new AuthResponse();
		authResponse.setJwt(token);
		authResponse.setMessage("Signup Success");
		
		return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.CREATED);
	}
	
	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> loginUserHandler(@RequestBody LoginRequest loginRequest){
		String username=loginRequest.getEmail();
		String password=loginRequest.getPassword();
		
		Authentication authentication=authenticate(username,password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token=jwtProvider.generateToken(authentication);
		AuthResponse authResponse=new AuthResponse();
		
		return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.OK);
	}

	private Authentication authenticate(String username, String password) {
		UserDetails userdetails=customeUserService.loadUserByUsername(username);
		
		if(userdetails==null) {
			throw new BadCredentialsException("Invalid Username...");
		}
		if(!passwordEncoder.matches(password, userdetails.getPassword())) {
			throw new BadCredentialsException("Invalid Password...");
		}
		return null;
	}
}
