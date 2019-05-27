package com.t13.dva.LAPS.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.t13.dva.LAPS.model.Compensation;
import com.t13.dva.LAPS.model.User;
import com.t13.dva.LAPS.repository.CompensationRepository;

@Service("compensationService")
public class CompensationServiceImp implements CompensationService{
	

	@Resource
	private CompensationRepository compensationRepository;
	@Resource
	private UserService userService;
	
	@Autowired
	public CompensationServiceImp (CompensationRepository compensationRepository) {
		this.compensationRepository = compensationRepository;
	}

	public void saveCompensation (Compensation compensation) {
		compensationRepository.save(compensation);
	}	

	public Compensation findCompensationById (int id) {
		return compensationRepository.findById(id).orElse(null);
	}

	public Page<Compensation> findCompensationsByUserid(Pageable pageable, int userid) {
		return compensationRepository.findByUserid(pageable, userid);
	}
	
	public List<Compensation> findCompensationsByUserid (int userid) {
		return compensationRepository.findByUserid(userid);
	}

	public Page<Compensation> findCompensationsByManagerid (Pageable pageable, int managerid) {
		List<User> users = userService.findUsersByManagerid(managerid);
		List<Compensation> compensations = new ArrayList<Compensation>();
		for (User u : users) {
			List<Compensation> ll = findCompensationsByUserid(u.getId());
			for (Compensation l : ll) {
				if (l.getStatus().equals("Applied")) {
					compensations.add(l);
				}
			}
		}
		int start = (int)pageable.getOffset();
	    int end = (start + pageable.getPageSize()) > compensations.size() ? compensations.size() : ( start + pageable.getPageSize());
	    return new PageImpl<Compensation>(compensations.subList(start, end), pageable, compensations.size());
	}
	
}
