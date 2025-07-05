package com.baumannibiuna.livrotarefa.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

import lombok.Data;



@Data
public class TaskDto {

	public TaskDto(Object object, String string, String string2, Object object2) {
		// TODO Auto-generated constructor stub
	}
	@Null(message = "O ID deve ser nulo para criaçao")
	private long id;
	
	@NotBlank(message = "Nome obrigatorio")
	@Size(max = 100, message = "Use no maximo 100 caracteres")
	private String name;
	
	@NotBlank(message = "Descricao obrigatoria")
	@Size(max = 150, message = "Use no maximo 150 caracteres")
	private String description;
	private String createdDate;
	
	
	
}
