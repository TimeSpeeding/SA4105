package com.t13.dva.LAPS.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.t13.dva.LAPS.model.Leave;

@Repository("leaveRepository")
public interface LeaveRepository extends JpaRepository<Leave, Integer> {
	
	Page<Leave> findByUserid(Pageable pageable, int userid);

	List<Leave> findByUserid(int userid);

}
