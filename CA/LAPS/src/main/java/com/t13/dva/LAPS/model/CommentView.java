package com.t13.dva.LAPS.model;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class CommentView {
	
	@NotEmpty(message = "* Please enter the comment!")
	private String comment;
	
}
