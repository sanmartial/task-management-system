package org.globaroman.taskmanagementsystem.controller;

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
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public TaskResponseDto create(@RequestBody CreateTaskRequireDto requireDto) {
        return taskService.create(requireDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{projectId}")
    public List<TaskResponseDto> getAllTask(@PathVariable Long projectId) {
        return taskService.getAllTasksByProjectId(projectId);
    }

    @GetMapping()
    public List<TaskResponseDto> getAllTask(Authentication authentication) {
        return taskService.getAllTasksByUserId(authentication);
    }

    @GetMapping("/task/{taskId}")
    public TaskResponseDto getAllTaskById(@PathVariable Long taskId,
                                          Authentication authentication) {
        return taskService.getTaskById(taskId, authentication);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{taskId}")
    public TaskResponseDto update(@PathVariable Long taskId,
                                  @RequestBody UpdateTaskRequireDto requireDto) {
        return taskService.update(taskId, requireDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{taskId}")
    public void delete(@PathVariable Long taskId) {
        taskService.deleteById(taskId);
    }
}
