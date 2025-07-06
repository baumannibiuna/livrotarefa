package com.baumannibiuna.livrotarefa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Data;

	@Entity
	@Data
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column
	@NotNull(message = "{NotNull.Task.name}")
	private String name;
	
	@Column
	@NotNull(message="{NotNull.Task.description}")
	private String description;


	
}
