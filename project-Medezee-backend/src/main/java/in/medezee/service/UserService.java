package in.medezee.service;

import in.medezee.exception.UserException;
import in.medezee.modal.User;

public interface UserService {
	
	public User findUserById(Long id) throws UserException;
	
	public User findUserProfileByJwt(String jwt) throws UserException;
}
