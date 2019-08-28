package com.codingdojo.gymbuddy.services;

import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingdojo.gymbuddy.models.Users;
import com.codingdojo.gymbuddy.repositories.UserRepository;

@Service
public class UserService {
	@Autowired
	private final UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
/////////////// Register User And Hash Password /////////////////////////////////////
	public Users save(Users users) {
		String hashed = BCrypt.hashpw(users.getPassword(), BCrypt.gensalt());
		users.setPassword(hashed);
		return userRepository.save(users);
	}
/////////////// Find Users By Email /////////////////////////////////////

	public Users findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
/////////////// Find Users By Id /////////////////////////////////////
	
	public Users findUserById(Long id) {
		Optional<Users> u = userRepository.findById(id);
		
		if (u.isPresent()) {
			return u.get();
		} else {
			return null;
		}
	} 
/////////////// Authenticate User /////////////////////////////////////
	
	public boolean authenticateUser(String email, String password) {
		Users users = userRepository.findByEmail(email);
		if(users == null) {
			return false;
		} else {
			if (BCrypt.checkpw(password, users.getPassword())) {
				return true;
			} else {
				return false;
			}
		}
	}

/////////////// Updating Users /////////////////////////////////////

	public void update(Users users) {
		userRepository.save(users);
	}
/////////////// Finding All Users /////////////////////////////////////
	
	public List<Users> allUsers() {
		return (List<Users>) userRepository.findAll();
	}
}

