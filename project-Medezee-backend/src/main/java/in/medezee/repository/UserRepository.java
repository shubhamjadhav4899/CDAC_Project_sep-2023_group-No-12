package in.medezee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.medezee.modal.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	public User findByEmail(String email);
}
