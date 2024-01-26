package org.globaroman.taskmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.globaroman.taskmanagementsystem.dto.task.CreateTaskRequireDto;
import org.globaroman.taskmanagementsystem.dto.task.TaskResponseDto;
import org.globaroman.taskmanagementsystem.exception.EntityNotFoundCustomException;
import org.globaroman.taskmanagementsystem.mapper.TaskMapper;
import org.globaroman.taskmanagementsystem.model.Label;
import org.globaroman.taskmanagementsystem.model.Project;
import org.globaroman.taskmanagementsystem.model.Status;
import org.globaroman.taskmanagementsystem.model.Task;
import org.globaroman.taskmanagementsystem.model.User;
import org.globaroman.taskmanagementsystem.repository.LabelRepository;
import org.globaroman.taskmanagementsystem.repository.ProjectRepository;
import org.globaroman.taskmanagementsystem.repository.TaskRepository;
import org.globaroman.taskmanagementsystem.service.TaskService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final LabelRepository labelRepository;

    @Override
    public TaskResponseDto create(CreateTaskRequireDto requireDto, Authentication authentication, Long projectId) {

        User user = (User) authentication.getPrincipal();

        Project project = getProjectById(projectId);

        Task task = new Task();
        task.setDate(LocalDateTime.now());
        task.setName(requireDto.getName());
        task.setDescription(requireDto.getDescription());
        task.setUser(user);
        task.setLabels(getLabels(requireDto.getLabelsIds()));
        task.setPriority(requireDto.getPriority());
        task.setStatus(Status.NOT_STARTED);

        Task savedTask = taskRepository.save(task);

        project.getTasks().add(savedTask);
        projectRepository.save(project);

        savedTask.setProject(project);
        Task updatedTask = taskRepository.save(savedTask);

        return taskMapper.toDto(updatedTask);
    }

    @Override
    public List<TaskResponseDto> getAllTasksByProjectId(Authentication authentication, Long projectId) {
        Project project = getProjectById(projectId);

        return taskRepository.findAllByProjectId(project.getId())
                .stream()
                .map(taskMapper::toDto)
                .toList();
    }

    private Set<Label> getLabels(Set<Long> labels) {
        return labels.stream()
                .map(labelRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }

    private Project getProjectById(Long projectId) {
        return projectRepository.findById(projectId).orElseThrow(
                () -> new EntityNotFoundCustomException("can not find project with id:" + projectId)
        );
    }
}
