package com.t13.dva.LAPS.model;

import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "compensation")
public class Compensation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "compensation_id")
	private int id;
	@Column(name = "date")
	private String date;
	@Column(name = "hour")
	private int hour;
	@Column(name = "reason")
	private String reason;
	@Column(name = "status")
	private String status;
	@Column(name = "user_id")
	private int userid;
	

	
}
