package com.t13.dva.LAPS.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.t13.dva.LAPS.model.Leave;
import com.t13.dva.LAPS.model.User;
import com.t13.dva.LAPS.repository.LeaveRepository;

@Service("leaveService")
public class LeaveServiceImp implements LeaveService{

	@Resource
	private LeaveRepository leaveRepository;	
	@Autowired
	private UserService userService;
	
	@Autowired
	public LeaveServiceImp (LeaveRepository leaveRepository, UserService userService) {
		this.leaveRepository = leaveRepository;
		this.userService = userService;
	}
	
	public Leave findLeaveById (Integer id) {
		return leaveRepository.findById(id).orElse(null);
	}
	
	public Page<Leave> findLeavesByUserid (Pageable pageable, int userid) {
		return leaveRepository.findByUserid(pageable, userid);
	}
	
	public List<Leave> findLeavesByUserid (int userid) {
		return leaveRepository.findByUserid(userid);
	}
	
	public List<Leave> findAllLeaves(Sort sort) {
		return leaveRepository.findAll(sort);
	}

	public void saveLeave(Leave leave) {
		leaveRepository.save(leave);
	}
	
	public Page<Leave> findLeavesByManagerid (Pageable pageable, int managerid) {
		List<User> users = userService.findUsersByManagerid(managerid);
		List<Leave> leaves = new ArrayList<Leave>();
		for (User u : users) {
			List<Leave> ll = findLeavesByUserid(u.getId());
			for (Leave l : ll) {
				if (l.getStatus().equals("Applied") || l.getStatus().equals("Updated")) {
					leaves.add(l);
				}
			}
		}
		int start = (int)pageable.getOffset();
	    int end = (start + pageable.getPageSize()) > leaves.size() ? leaves.size() : ( start + pageable.getPageSize());
	    return new PageImpl<Leave>(leaves.subList(start, end), pageable, leaves.size());
	}

}
