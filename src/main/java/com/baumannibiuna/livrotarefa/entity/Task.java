package com.baumannibiuna.livrotarefa.entity;

import lombok.Data;

import java.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

	private LocalDateTime createdDate;
	
	public Task(Long id, String name, String description, LocalDateTime createdDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdDate = createdDate;
    }
	
	
}
