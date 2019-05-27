package com.t13.dva.LAPS.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.t13.dva.LAPS.model.Compensation;

public interface CompensationService {
	
	public void saveCompensation (Compensation compensation);
	public Compensation findCompensationById (int id);
	public Page<Compensation> findCompensationsByUserid(Pageable pageable, int userid);
	public List<Compensation> findCompensationsByUserid (int userid);
	public Page<Compensation> findCompensationsByManagerid (Pageable pageable, int managerid);
	
}
