package com.baumannibiuna.livrotarefa.dto;


import javax.validation.constraints.NotBlank;

import lombok.Data;



@Data
public class TaskDto {

	
	private long id;
	@NotBlank String name;
	@NotBlank String description;
	private String createdDate;
	
	
	
}
