package org.globaroman.taskmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.globaroman.taskmanagementsystem.dto.project.CreateProjectRequestDto;
import org.globaroman.taskmanagementsystem.dto.project.ProjectResponseDto;
import org.globaroman.taskmanagementsystem.service.ProjectService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ProjectResponseDto create(@RequestBody CreateProjectRequestDto requestDto,
                                     Authentication authentication) {
        return projectService.create(requestDto, authentication);
    }

    @GetMapping
    public List<ProjectResponseDto> getAll(Authentication authentication) {
        return projectService.getAll(authentication);
    }
}
