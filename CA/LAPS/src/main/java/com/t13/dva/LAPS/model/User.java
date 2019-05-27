package com.t13.dva.LAPS.model;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private int id;
	@Column(name = "name")
	@NotEmpty(message = "* Please provide a name")
	private String name;
	@Column(name = "email")
	@Email(message = "* Please provide a valid Email")
	@NotEmpty(message = "* Please provide an email")
	private String email;
	@Column(name = "username")
	@NotEmpty(message = "* Please provide a username")
	private String username;
	@Column(name = "password")
	@Length(min = 3, message = "* The password must have at least 3 characters")
	@NotEmpty(message = "* Please provide a password")
	private String password;
	@Column(name = "manager_id")
	private int managerid;
	@Column(name = "role_id")
	private int roleid;
	@Column(name = "active")
	private int active;
	@Column(name = "over_work_hour")
	private int overworkhour;
	@Column(name = "annual_leave_day")
	private int annualleaveday;
	@Column(name = "medical_leave_day")
	private int medicalleaveday;
	
	
}
