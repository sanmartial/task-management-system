package org.globaroman.taskmanagementsystem.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.globaroman.taskmanagementsystem.dto.project.CreateProjectRequestDto;
import org.globaroman.taskmanagementsystem.dto.project.ProjectResponseDto;
import org.globaroman.taskmanagementsystem.exception.EntityNotFoundCustomException;
import org.globaroman.taskmanagementsystem.mapper.ProjectMapper;
import org.globaroman.taskmanagementsystem.model.Project;
import org.globaroman.taskmanagementsystem.model.Status;
import org.globaroman.taskmanagementsystem.model.User;
import org.globaroman.taskmanagementsystem.repository.ProjectRepository;
import org.globaroman.taskmanagementsystem.service.ProjectService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    @Override
    public ProjectResponseDto create(CreateProjectRequestDto requestDto,
                                     Authentication authentication) {
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
    public List<ProjectResponseDto> getAll() {
        return projectRepository.findAll().stream()
                .map(projectMapper::toDto)
                .toList();
    }

    @Override
    public ProjectResponseDto getProjectById(Long projectId) {
        Project project = getExistProjectById(projectId);

        return projectMapper.toDto(project);
    }

    @Override
    public ProjectResponseDto update(Long projectId, CreateProjectRequestDto requestDto) {
        Project project = getExistProjectById(projectId);

        project.setName(requestDto.getName());
        project.setDescription(requestDto.getDescription());
        project.setEndDate(project.getStartDate().plusDays(requestDto.getDurationInDay()));

        return projectMapper.toDto(projectRepository.save(project));
    }

    @Override
    public void deleteById(Long projectId) {
        projectRepository.deleteById(projectId);
    }

    private Project getExistProjectById(Long projectId) {
        return projectRepository.findById(projectId).orElseThrow(
                () -> new EntityNotFoundCustomException("Can not project by id:" + projectId)
        );
    }
}
