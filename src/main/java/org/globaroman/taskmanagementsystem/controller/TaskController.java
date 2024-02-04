package org.globaroman.taskmanagementsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.globaroman.taskmanagementsystem.dto.task.CreateTaskRequireDto;
import org.globaroman.taskmanagementsystem.dto.task.TaskResponseDto;
import org.globaroman.taskmanagementsystem.dto.task.UpdateTaskRequireDto;
import org.globaroman.taskmanagementsystem.service.TaskService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Tasks", description = "Operations related to tasks")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "Create a new task")
    public TaskResponseDto create(@RequestBody CreateTaskRequireDto requireDto) {

        return taskService.create(requireDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{projectId}")
    @Operation(summary = "Get all tasks. Allowed by administrator")
    public List<TaskResponseDto> getAllTask(@PathVariable Long projectId) {

        return taskService.getAllTasksByProjectId(projectId);
    }

    @GetMapping()
    @Operation(summary = "Get all tasks for a specific user")
    public List<TaskResponseDto> getAllTask(Authentication authentication) {

        return taskService.getAllTasksByUserId(authentication);
    }

    @GetMapping("/task/{taskId}")
    @Operation(summary = "Get a task by id")
    public TaskResponseDto getTaskById(@PathVariable Long taskId,
                                       Authentication authentication) {

        return taskService.getTaskById(taskId, authentication);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{taskId}")
    @Operation(summary = "Update an exist task")
    public TaskResponseDto update(@PathVariable Long taskId,
                                  @RequestBody UpdateTaskRequireDto requireDto) {

        return taskService.update(taskId, requireDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{taskId}")
    @Operation(summary = "Delete an exist task by id")
    public void delete(@PathVariable Long taskId) {

        taskService.deleteById(taskId);
    }
}
