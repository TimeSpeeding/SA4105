package com.t13.dva.LAPS.model;

import java.time.LocalDate;

import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "leavedetail")
public class Leave {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "leave_id")
	private int id;
	@Column(name = "start_date")
	private LocalDate startDate;
	@Column(name = "end_date")
	private LocalDate endDate;
	@Column(name = "category")
	private String category;
	@Column(name = "reason")
	private String reason;
	@Column(name = "contact_detail")
	private String contactDetail;
	@Column(name = "status")
	private String status;
	@Column(name = "user_id")
	private int userid;
	@Column(name = "work_dis")
	private String workdis;
	@Column(name = "comment")
	private String comment;
}
