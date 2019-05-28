package com.t13.dva.LAPS.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
@Entity
@Table(name = "holiday")
public class Holiday {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "holiday_id")
	private int id;
	@Column(name = "holiday_date")
	@NotEmpty(message = "* Please choose a date!")
	private String date;
	
}
