package com.baumannibiuna.livrotarefa.mock;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import com.baumannibiuna.livrotarefa.entity.Task;
import java.util.ArrayList;
import java.util.List;

public class TaskMock {

	public static Page<Task> createTasks() {

		List<Task> taskList = new ArrayList<>();
		Task task1 = new Task();
		task1.setId(1);
		task1.setName("Tarefa 1");
		task1.setDescription("Descricao da tarefa 1");

		Task task2 = new Task();
		task2.setId(1);
		task2.setName("Tarefa 2");
		task2.setDescription("Descricao da tarefa 2");
		
		taskList.add(task1);
		taskList.add(task2);
		Page<Task> pagedResponse = new  PageImpl<>(taskList);	

		return pagedResponse;
	}

}
