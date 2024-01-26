package org.globaroman.taskmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.globaroman.taskmanagementsystem.dto.task.CreateTaskRequireDto;
import org.globaroman.taskmanagementsystem.dto.task.TaskResponseDto;
import org.globaroman.taskmanagementsystem.service.TaskService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping("/{projectId}")
    public TaskResponseDto create(@RequestBody CreateTaskRequireDto requireDto,
                                  Authentication authentication,
                                  @PathVariable Long projectId) {
        System.out.println(requireDto);
        return taskService.create(requireDto, authentication, projectId);
    }

    @GetMapping("/{projectId}")
    public List<TaskResponseDto> getAllTask(@PathVariable Long projectId,
                                            Authentication authentication) {
        return taskService.getAllTasksByProjectId(authentication, projectId);
    }


//    GET: /api/tasks - Retrieve tasks for a project
//    GET: /api/tasks/{id} - Retrieve task details
//    PUT: /api/tasks/{id} - Update task
//    DELETE: /api/tasks/{id} - Delete task
}
