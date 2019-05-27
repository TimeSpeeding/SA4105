package com.t13.dva.LAPS.model;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "role")
public class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "role_id")
	private int id;
	@Column(name = "name")
	private String name;
}
