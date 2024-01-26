package org.globaroman.taskmanagementsystem.service;

import java.util.List;
import org.globaroman.taskmanagementsystem.dto.project.CreateProjectRequestDto;
import org.globaroman.taskmanagementsystem.dto.project.ProjectResponseDto;
import org.springframework.security.core.Authentication;

public interface ProjectService {
    ProjectResponseDto create(CreateProjectRequestDto requestDto, Authentication authentication);

    List<ProjectResponseDto> getAll(Authentication authentication);
}
