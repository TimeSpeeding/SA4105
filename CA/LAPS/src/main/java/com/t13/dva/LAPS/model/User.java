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
	@Column(name = "userid")
	private int id;
	@Column(name = "name")
	@NotEmpty(message = "* Please provide a name")
	private String name;
	@Column(name = "email")
	@Email(message = "* Please provide a valid Email")
	private String email;
	@Column(name = "username")
	@NotEmpty(message = "* Please provide a username")
	private String username;
	@Column(name = "password")
	@Length(min = 6, message = "The password must have at least 6 characters")
	@NotEmpty(message = "* Please provide a password")
	private String password;
	@Column(name = "Managerid")
	private int managerid;
	@Column(name = "roleid")
	private int roleid;
	@Column(name = "active")
	private int active;
	
}
