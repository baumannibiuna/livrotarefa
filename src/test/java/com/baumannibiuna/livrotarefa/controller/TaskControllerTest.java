package com.baumannibiuna.livrotarefa.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
 * Testes unitários para TaskController Foco no comportamento dos endpoints REST
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
        // Configura dados de teste
        sampleTask = new Task();
        sampleTaskDto = new TaskDto();
        sampleTaskDto.setId(0);
        sampleTaskDto.setName("Tarefa Teste");
        sampleTaskDto.setDescription("Descrição");
    }

    @Test
    void createTask_WithoutName_ShouldReturnBadRequest() throws Exception {
        TaskDto invalidDto = new TaskDto();
        invalidDto.setDescription("Descrição");
        
        mockMvc.perform(post("/livrotarefa/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDto)))
            .andExpect(status().isBadRequest());
    }
    
    @Test
    void createTask_WithValidData_ShouldReturnCreatedResource() throws Exception {
        // Configura o mock usando any() em vez de argThat
        when(taskService.saveTask(any(TaskDto.class))).thenReturn(sampleTask);
        
        // 2. Usa o objectMapper para garantir serialização consistente
        String requestBody = objectMapper.writeValueAsString(sampleTaskDto);

        // Act & Assert
        // 3. Executa o teste com os asserts corrigidos
        mockMvc.perform(post("/livrotarefa/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(sampleTask.getId())) // Usa o ID da sampleTask
            .andExpect(jsonPath("$.name").value(sampleTask.getName())) // Usa o name da sampleTask
            .andExpect(jsonPath("$.description").value(sampleTask.getDescription()));
    }
    // Versão alternativa se precisar validar o conteúdo do DTO

	@Test
    void createTask_WithValidData_AlternativeValidation() throws Exception {
        // Configura o mock para capturar o argumento
        // Garanta que o DTO de teste está válido
        TaskDto validDto = new TaskDto();
        validDto.setName("Tarefa Teste"); // CAMPO OBRIGATÓRIO
        validDto.setDescription("Descrição");
        validDto.setId(0); // Para operação de criação
        TaskDto[] capturedDto = new TaskDto[1];
        when(taskService.saveTask(any(TaskDto.class))).thenAnswer(invocation -> {
            capturedDto[0] = invocation.getArgument(0);
            return sampleTask;
        });

        // Act
        mockMvc.perform(post("/livrotarefa/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validDto)))
            .andExpect(status().isCreated());

        // Assert adicional para verificar o DTO recebido
        assertNull(capturedDto[0].getId(0));
        assertEquals("Tarefa Teste", capturedDto[0].getName());
        assertEquals("Descrição", capturedDto[0].getDescription());
    }

    // Outros testes podem permanecer como estão
    @Test
    void getTasks_ShouldReturnPaginatedTasks() throws Exception {
        Page<Task> page = new PageImpl<>(List.of(sampleTask));
        PagedModel<EntityModel<Task>> pagedModel = PagedModel.empty();

        when(taskService.getTasks(any(Pageable.class))).thenReturn(page);
        when(pagedResourcesAssembler.toModel(eq(page), any(Link.class))).thenReturn(pagedModel);

        mockMvc.perform(MockMvcRequestBuilders.get("/livrotarefa/tasks")
                .param("page", "0")
                .param("size", "10"))
            .andExpect(status().isOk());
    }
}