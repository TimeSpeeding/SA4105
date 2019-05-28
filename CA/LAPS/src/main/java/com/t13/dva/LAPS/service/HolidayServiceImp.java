package com.t13.dva.LAPS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.t13.dva.LAPS.model.Holiday;
import com.t13.dva.LAPS.repository.HolidayRepository;

@Service
public class HolidayServiceImp implements HolidayService{
	
	@Autowired
	HolidayRepository holidayRepository;
		
	public Page<Holiday> findAllHolidays (Pageable pageable) {
		return holidayRepository.findAll(pageable);
	}
	
	public void saveHoliday (Holiday holiday) {
		holidayRepository.save(holiday);
	}
	
	public Holiday findHolidayById (int id) {
		return holidayRepository.findById(id).orElse(null);
	}
	
	public void deleteHoliday (Holiday holiday) {
		holidayRepository.delete(holiday);
	}
	
	public List<Holiday> findAllHolidays () {
		return holidayRepository.findAll();
	}
	
	public Holiday findHolidayByDate (String date) {
		return holidayRepository.findByDate(date);
	}

}
