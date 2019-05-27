package com.t13.dva.LAPS.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.t13.dva.LAPS.model.User;

public interface UserService {

	public User findUserByUsername (String username);
	public void saveUser(User user);
	public Page<User> findAllusers (Pageable pageable);
	public User findUserById (int id);
	public void deleteUser (User user);
	public List<User> findUsersByManagerid (int managerid);
	public void editUser(User user);
	
}
