package com.t13.dva.LAPS.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.t13.dva.LAPS.model.Holiday;

public class CalculateWorkDays {
	
	public int getWorkDays (LocalDate startDate, LocalDate endDate, List<Holiday> holidays) {

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		long days = endDate.toEpochDay() - startDate.toEpochDay() + 1;
		int workdays = 0;
		LocalDate today = startDate;
		for (int i = 0; i < days; i++) {
			if (today.getDayOfWeek() != DayOfWeek.SATURDAY && today.getDayOfWeek() != DayOfWeek.SUNDAY) {
				workdays++;
			} else {
				for (Holiday h : holidays) {
					if (h.getDate().equals(today.format(dateTimeFormatter))) {
						workdays--;
					}
				}
			}
			today = today.plusDays(1);
		}
		return workdays;
	}
}
