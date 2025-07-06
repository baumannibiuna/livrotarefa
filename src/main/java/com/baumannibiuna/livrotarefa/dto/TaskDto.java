package com.baumannibiuna.livrotarefa.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;



@Data
public class TaskDto {

	public TaskDto() {
		// TODO Auto-generated constructor stub
	}

	private long id;
	
	@NotBlank(message = "Nome obrigatorio")
	@Size(max = 100, message = "Use no maximo 100 caracteres")
	private String name;
	
	@NotBlank(message = "Descricao obrigatoria")
	@Size(max = 150, message = "Use no maximo 150 caracteres")
	private String description;

	public void setId(Object object) {
		// TODO Auto-generated method stub
		
	}

	public Object getId(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
}
