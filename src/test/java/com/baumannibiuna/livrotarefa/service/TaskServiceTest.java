package com.baumannibiuna.livrotarefa.service;

import com.baumannibiuna.livrotarefa.mock.TaskMock;
import com.baumannibiuna.livrotarefa.entity.Task;
import com.baumannibiuna.livrotarefa.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.platform.runner.JUnitPlatform;
//import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(MockitoExtension.class)
//@RunWith(JUnitPlatform.class)
public class TaskServiceTest {
	
	@Mock
	TaskRepository taskRepository;
	private TaskService taskService;
	
	@BeforeEach
	public void Setup() {
		
		taskService = new TaskService(taskRepository);
		Pageable pageable = PageRequest.of(0, 5, Sort.by(
							Sort.Order.asc("name"),
							Sort.Order.desc("id")));
		Mockito.lenient().when(taskRepository.findAll(pageable)).thenReturn(TaskMock.createTasks());
	}
	
	@Test
	@DisplayName("Should return all Tasks")
	public void getTask_heppypath() {
		
		Pageable pageable = PageRequest.of(0, 5, Sort.by(
							Sort.Order.asc("name"),
							Sort.Order.desc("id")));
		
		Page<Task> tasks = taskService.getTasks(pageable);
		
		assertEquals(tasks.getTotalPages(), 1);
		assertEquals(tasks.getNumberOfElements(), 2);
		assertNotNull(tasks);
		
	}
	
	@Nested
	@DisplayName("Happy Tests")
	class happycases {
		
		@Test
		void justtest() {
			String name = "just testing";
			assertEquals(name, "just testing");
		}
		
		@Test
		void justtest1() {
			String name = "just testin";
			assertEquals(name, "just testin");
		}
	}
	
	@Nested
	@DisplayName("Unhappy Tests")
	class unhappycases{
		@Test
		void justtest() {
			String name = "just testing";
			assertEquals(name, "just testing");
		}
		
		@Test
		void justtest1() {
			String name = "just testing";
			assertEquals(name, "just testing");
		}
	}

}
