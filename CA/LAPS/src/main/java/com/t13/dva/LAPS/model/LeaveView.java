package com.t13.dva.LAPS.model;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class LeaveView {

	@NotEmpty(message = "* Please choose a start date!")
	private String startDate;
	@NotEmpty(message = "* Please choose a end date!")
	private String endDate;
	@NotEmpty(message = "* Please choose a category")
	private String category;
	private String reason;
	private String contactDetail;
	private String workdis;
	
}
