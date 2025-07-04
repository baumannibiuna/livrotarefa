package com.baumannibiuna.livrotarefa.uri;

import org.springframework.hateoas.server.EntityLinks;
import org.springframework.stereotype.Component;

@Component
public class TaskUri {

	private final EntityLinks entityLinks;
	
	public static final String TASKS = "/tasks";
	public static final String TASK = "/tasks/{id}";
	public static final String CREATE_TASK = "/tasks";
	
	public TaskUri(EntityLinks entityLinks) {
		this.entityLinks = entityLinks;
	}
}
