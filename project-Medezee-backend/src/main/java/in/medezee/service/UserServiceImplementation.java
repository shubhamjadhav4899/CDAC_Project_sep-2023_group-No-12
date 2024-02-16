package in.medezee.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import in.medezee.config.JwtProvider;
import in.medezee.exception.UserException;
import in.medezee.modal.User;
import in.medezee.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {

	private final UserRepository userRepository;
	
	private final JwtProvider jwtProvider;
	
	@Override
	public User findUserById(Long id) throws UserException {
		Optional<User>user=userRepository.findById(id);
		if(user.isPresent()) {
			return user.get();
		}
		throw new UserException("User not found with id : "+id);
	}

	@Override
	public User findUserProfileByJwt(String jwt) throws UserException {
		String email=jwtProvider.getEmailFromToken(jwt);
		
		User user=userRepository.findByEmail(email);
		
		if(user==null) {
			throw new UserException("User Not found with email : "+email);
		}
		return user;
	}

}
