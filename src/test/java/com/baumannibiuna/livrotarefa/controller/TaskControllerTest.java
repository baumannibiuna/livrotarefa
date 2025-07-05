package com.baumannibiuna.livrotarefa.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.baumannibiuna.livrotarefa.dto.TaskDto;
import com.baumannibiuna.livrotarefa.entity.Task;
import com.baumannibiuna.livrotarefa.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Testes unitários para TaskController
 * Foco no comportamento dos endpoints REST
 */
@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @MockBean
    private PagedResourcesAssembler<Task> pagedResourcesAssembler;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private Task sampleTask;
    private TaskDto sampleTaskDto;

    @BeforeEach
    void setUp() {
        // Configura dados de teste reutilizáveis
        sampleTask = new Task(1L, "Tarefa Teste", "Descrição", LocalDateTime.now());
        sampleTaskDto = new TaskDto(null, "Tarefa Teste", "Descrição", null);
    }

    /**
     * Teste: GET /livrotarefa/tasks
     * Deve retornar lista paginada de tarefas com status 200
     */
    @Test
    void getTasks_ShouldReturnPaginatedTasks() throws Exception {
        // Arrange - Configura mock para retornar página com 1 tarefa
        Page<Task> page = new PageImpl<>(List.of(sampleTask));
        PagedModel<EntityModel<Task>> pagedModel = PagedModel.empty();
        
        when(taskService.getTasks(any(Pageable.class))).thenReturn(page);
        when(pagedResourcesAssembler.toModel(eq(page), any(Link.class))).thenReturn(pagedModel);

        // Act & Assert - Executa requisição e verifica resultados
        mockMvc.perform(MockMvcRequestBuilders.get("/livrotarefa/tasks")
                .param("page", "0")
                .param("size", "10"))
            .andExpect(status().isOk());
    }

    /**
     * Teste: GET /livrotarefa/tasks/{id} com ID existente
     * Deve retornar a tarefa correspondente com status 200 e links HATEOAS
     */
    @Test
    void createTask_WithValidData_ShouldReturnCreatedResource1() throws Exception {
        // Arrange
        String validJson = """
            {
                "name": "Tarefa Teste",
                "description": "Descrição válida"
            }
            """;
        
        when(taskService.saveTask(any(TaskDto.class)))
            .thenReturn(sampleTask);

        // Act & Assert
        mockMvc.perform(post("/livrotarefa/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(validJson))
            .andExpect(status().isCreated());
    }

    /**
     * Teste: GET /livrotarefa/tasks/{id} com ID inexistente
     * Deve retornar status 404 (Not Found)
     */
    @Test
    void getTask_WithNonExistingId_ShouldReturn404() throws Exception {
        // Arrange - Configura mock para lançar exceção quando buscar por ID 99
        when(taskService.getTask(99L))
            .thenThrow(new RuntimeException("Task Not Found"));

        // Act & Assert - Verifica se retorna 404
        mockMvc.perform(MockMvcRequestBuilders.get("/livrotarefa/tasks/99"))
            .andExpect(status().isNotFound());
    }

    /**
     * Teste: POST /livrotarefa/tasks com dados válidos
     * Deve:
     * 1. Retornar status 201 (Created)
     * 2. Incluir location header com URI do novo recurso
     * 3. Retornar os dados da tarefa criada com links HATEOAS
     */
    @Test
    void createTask_WithValidData_ShouldReturnCreatedResource() throws Exception {
        // Arrange - Configura mock para retornar tarefa salva
        when(taskService.saveTask(any(TaskDto.class))).thenReturn(sampleTask);

        // Act & Assert - Executa POST e verifica resposta
        mockMvc.perform(MockMvcRequestBuilders.post("/livrotarefa/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sampleTaskDto)))
            .andExpect(status().isCreated())
            .andExpect(header().exists("Location"))
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$._links.self.href").exists())
            .andExpect(jsonPath("$._links.all-tasks.href").exists());
    }

    /**
     * Teste: POST /livrotarefa/tasks com dados inválidos (nome vazio)
     * Deve retornar status 400 (Bad Request)
     */
    @Test
    void createTask_WithEmptyName_ShouldReturn400() throws Exception {
        // Arrange - Cria DTO com nome vazio (inválido)
        TaskDto invalidDto = new TaskDto(null, "", "Descrição", null);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/livrotarefa/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDto)))
            .andExpect(status().isBadRequest());
    }
}