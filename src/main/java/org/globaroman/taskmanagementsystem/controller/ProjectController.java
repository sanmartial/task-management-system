package org.globaroman.taskmanagementsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.globaroman.taskmanagementsystem.dto.project.CreateProjectRequestDto;
import org.globaroman.taskmanagementsystem.dto.project.ProjectResponseDto;
import org.globaroman.taskmanagementsystem.service.ProjectService;
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

@Tag(name = "Projects", description = "Operations related the projects")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "Create a new project")
    public ProjectResponseDto create(@RequestBody CreateProjectRequestDto requestDto,
                                     Authentication authentication) {
        return projectService.create(requestDto, authentication);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    @Operation(summary = "Get all projects")
    public List<ProjectResponseDto> getAll() {
        return projectService.getAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{projectId}")
    @Operation(summary = "Get a project by id")
    public ProjectResponseDto getProjectById(@PathVariable Long projectId) {
        return projectService.getProjectById(projectId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{projectId}")
    @Operation(summary = "Update an exist project")
    public ProjectResponseDto update(@PathVariable Long projectId,
                                     @RequestBody CreateProjectRequestDto requestDto) {
        return projectService.update(projectId, requestDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{projectId}")
    @Operation(summary = "Delete an exist project ")
    public void deleteById(@PathVariable Long projectId) {
        projectService.deleteById(projectId);
    }
}
