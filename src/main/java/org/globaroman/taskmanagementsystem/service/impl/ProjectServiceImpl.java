package org.globaroman.taskmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.globaroman.taskmanagementsystem.mapper.ProjectMapper;
import org.globaroman.taskmanagementsystem.dto.project.CreateProjectRequestDto;
import org.globaroman.taskmanagementsystem.model.Project;
import org.globaroman.taskmanagementsystem.dto.project.ProjectResponseDto;
import org.globaroman.taskmanagementsystem.model.Status;
import org.globaroman.taskmanagementsystem.model.User;
import org.globaroman.taskmanagementsystem.repository.ProjectRepository;
import org.globaroman.taskmanagementsystem.service.ProjectService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    private final ProjectMapper projectMapper;

    @Override
    public ProjectResponseDto create(CreateProjectRequestDto requestDto, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Project project = new Project();
        project.setName(requestDto.getName());
        project.setDescription(requestDto.getDescription());
        project.setStartDate(LocalDateTime.now());
        project.setEndDate(LocalDateTime.now().plusDays(requestDto.getDurationInDay()));
        project.setStatus(Status.INITIATED);
        project.setUser(user);

        return projectMapper.toDto(projectRepository.save(project));
    }

    @Override
    public List<ProjectResponseDto> getAll(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return projectRepository.findAllByUserId(user.getId()).stream()
                .map(projectMapper::toDto)
                .toList();
    }
}
