package com.baumannibiuna.livrotarefa.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.baumannibiuna.livrotarefa.dto.TaskDto;
import com.baumannibiuna.livrotarefa.entity.Task;
import com.baumannibiuna.livrotarefa.service.TaskService;
import com.baumannibiuna.livrotarefa.uri.TaskUri;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/livrotarefa")
@RequiredArgsConstructor
public class TaskController {

    private final PagedResourcesAssembler<Task> pagedResourcesAssembler;
    private final TaskService taskService;

    @GetMapping(path = TaskUri.TASKS)
    public ResponseEntity<?> getTasks(Pageable pageable,
            Link resourceAssembler) {
        Page<Task> tasks = taskService.getTasks(pageable);
        PagedModel<?> resource = pagedResourcesAssembler.toModel(tasks, resourceAssembler);
        return ResponseEntity.ok(resource);
    }

    @GetMapping(path = TaskUri.TASK)
    public ResponseEntity<?> getTask(@PathVariable("id") Long taskId, 
            Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        try {
            Task task = taskService.getTask(taskId);
            Link selfLink = linkTo(methodOn(this.getClass())
                .getTask(taskId, pageable, resourceAssembler)).withSelfRel();
            Link allTasksLink = linkTo(this.getClass()).slash("/tasks").withRel("all-tasks");

            EntityModel<Task> entityModel = EntityModel.of(task);
            entityModel.add(selfLink, allTasksLink);

            return ResponseEntity.ok(entityModel);

        } catch (RuntimeException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task Not Found", exc);
        }
    }

    @PostMapping(path = TaskUri.CREATE_TASK)
    public ResponseEntity<?> createTask(@Valid @RequestBody TaskDto taskDto) {
        Task savedTask = taskService.saveTask(taskDto);
        
        Link selfLink = linkTo(methodOn(this.getClass())
            .getTask(savedTask.getId(), null, null)).withSelfRel();
        Link allTasksLink = linkTo(this.getClass()).slash("/tasks").withRel("all-tasks");
        
        EntityModel<Task> resource = EntityModel.of(savedTask);
        resource.add(selfLink, allTasksLink);
        
        return ResponseEntity.created(selfLink.toUri()).body(resource);
    }
}