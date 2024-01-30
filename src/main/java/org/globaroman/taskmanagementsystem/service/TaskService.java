package org.globaroman.taskmanagementsystem.service;

import java.util.List;
import org.globaroman.taskmanagementsystem.dto.task.CreateTaskRequireDto;
import org.globaroman.taskmanagementsystem.dto.task.TaskResponseDto;
import org.globaroman.taskmanagementsystem.dto.task.UpdateTaskRequireDto;
import org.springframework.security.core.Authentication;

public interface TaskService {
    TaskResponseDto create(CreateTaskRequireDto requireDto);

    List<TaskResponseDto> getAllTasksByProjectId(Long projectId);

    TaskResponseDto update(Long taskId, UpdateTaskRequireDto requireDto);

    void deleteById(Long taskId);

    TaskResponseDto getTaskById(Long taskId, Authentication authentication);

    List<TaskResponseDto> getAllTasksByUserId(Authentication authentication);
}
