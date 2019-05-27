package com.t13.dva.LAPS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.t13.dva.LAPS.model.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Integer> {
	
	User findByUsername(String username);
	List<User> findByManagerid(int managerid);

}
