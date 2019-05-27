package com.t13.dva.LAPS.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.t13.dva.LAPS.model.User;
import com.t13.dva.LAPS.repository.UserRepository;

@Service("userService")
public class UserServiceImp implements UserService{
	
	@Resource
	private UserRepository userRepository;	
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	public UserServiceImp(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
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

	public Page<User> findAllusers (Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	public User findUserById (int id) {
		return userRepository.findById(id).orElse(null);
	}
	
	public List<User> findUsersByManagerid (int managerid) {
		return userRepository.findByManagerid(managerid);
	}
	
	public void deleteUser (User user) {
		userRepository.delete(user);
	}
	
}
