package com.t13.dva.LAPS.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.t13.dva.LAPS.model.Holiday;

public interface HolidayService {
	
	public Page<Holiday> findAllHolidays (Pageable pageable);
	public void saveHoliday (Holiday holiday);
	public Holiday findHolidayById (int id);
	public void deleteHoliday (Holiday holiday);
	public List<Holiday> findAllHolidays();

}
