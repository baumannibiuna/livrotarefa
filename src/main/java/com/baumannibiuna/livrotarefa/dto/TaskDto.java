package com.baumannibiuna.livrotarefa.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

import lombok.Data;



@Data
public class TaskDto {

	
	@Null private long id;
	@NotBlank String name;
	@NotBlank String description;
	private String createdDate;
	
	
	
}
