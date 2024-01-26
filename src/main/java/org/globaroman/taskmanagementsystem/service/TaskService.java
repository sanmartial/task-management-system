package org.globaroman.taskmanagementsystem.service;

import java.util.List;
import org.globaroman.taskmanagementsystem.dto.task.CreateTaskRequireDto;
import org.globaroman.taskmanagementsystem.dto.task.TaskResponseDto;
import org.springframework.security.core.Authentication;

public interface TaskService {
    TaskResponseDto create(CreateTaskRequireDto requireDto,
                           Authentication authentication, Long projectId);

    List<TaskResponseDto> getAllTasksByProjectId(Authentication authentication, Long projectId);
}
