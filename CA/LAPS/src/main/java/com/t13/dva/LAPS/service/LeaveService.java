package com.t13.dva.LAPS.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.t13.dva.LAPS.model.Leave;

public interface LeaveService {

	public Leave findLeaveById (Integer id);
	public Page<Leave> findLeavesByUserid (Pageable pageable, int userid);
	public void saveLeave(Leave leave);
	public List<Leave> findLeavesByUserid (int userid);
	public Page<Leave> findLeavesByManagerid (Pageable pageable, int managerid);
	public List<Leave> findAllLeaves(Sort sort);
	
}
