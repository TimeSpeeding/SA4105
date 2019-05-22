package com.t13.dva.LAPS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.t13.dva.LAPS.model.User;
import com.t13.dva.LAPS.repository.UserRepository;

@Service("userService")
public class UserService {
	
	private UserRepository userRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	public User findUserByUsername (String username) {
		return userRepository.findByUsername(username);
	}
	
	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setActive(1);
		userRepository.save(user);
	}
	
	public int checkpassword(String username, String password) {

		User user1 = findUserByUsername(username);
		if (user1 != null) {
			if (password.equalsIgnoreCase(user1.getPassword())) {
				return (user1.getRoleid());
			} else
				return 0;
		}
		return 5;
	}
}
