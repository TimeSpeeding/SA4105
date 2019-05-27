package com.t13.dva.LAPS.util;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class CalculateWorkDays {
	
	public int getWorkDays (LocalDate startDate, LocalDate endDate) {
		
		long days = endDate.toEpochDay() - startDate.toEpochDay() + 1;
		int workdays = (int) days / 7 * 5;
		for (int i = 0; i < days % 7; i++) {
			LocalDate today = startDate;
			if (today.getDayOfWeek() != DayOfWeek.SATURDAY && today.getDayOfWeek() != DayOfWeek.SUNDAY) {
				workdays++;
			}
			today = today.plusDays(1);
		}
		return workdays;
	}
}
