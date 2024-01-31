package org.globaroman.taskmanagementsystem.service.impl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.globaroman.taskmanagementsystem.dto.task.CreateTaskRequireDto;
import org.globaroman.taskmanagementsystem.dto.task.TaskResponseDto;
import org.globaroman.taskmanagementsystem.dto.task.UpdateTaskRequireDto;
import org.globaroman.taskmanagementsystem.exception.EntityNotFoundCustomException;
import org.globaroman.taskmanagementsystem.exception.UserCredentialException;
import org.globaroman.taskmanagementsystem.mapper.TaskMapper;
import org.globaroman.taskmanagementsystem.model.Label;
import org.globaroman.taskmanagementsystem.model.Project;
import org.globaroman.taskmanagementsystem.model.Role;
import org.globaroman.taskmanagementsystem.model.RoleName;
import org.globaroman.taskmanagementsystem.model.Status;
import org.globaroman.taskmanagementsystem.model.Task;
import org.globaroman.taskmanagementsystem.model.User;
import org.globaroman.taskmanagementsystem.repository.LabelRepository;
import org.globaroman.taskmanagementsystem.repository.ProjectRepository;
import org.globaroman.taskmanagementsystem.repository.RoleRepository;
import org.globaroman.taskmanagementsystem.repository.TaskRepository;
import org.globaroman.taskmanagementsystem.repository.UserRepository;
import org.globaroman.taskmanagementsystem.service.TaskService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final LabelRepository labelRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public TaskResponseDto create(CreateTaskRequireDto requireDto) {

        User user = getExistUserById(requireDto.getUserId());

        Task task = new Task();
        task.setDate(LocalDateTime.now());
        task.setName(requireDto.getName());
        task.setDescription(requireDto.getDescription());
        task.setUser(user);
        task.setLabels(getListLabelsByListLabelIds(requireDto.getLabelsIds()));
        task.setPriority(requireDto.getPriority());
        task.setStatus(Status.NOT_STARTED);

        Task savedTask = taskRepository.save(task);

        Project project = getProjectById(requireDto.getProjectId());
        project.getTasks().add(savedTask);
        projectRepository.save(project);

        savedTask.setProject(project);
        Task updatedTask = taskRepository.save(savedTask);

        return taskMapper.toDto(updatedTask);
    }

    @Override
    public List<TaskResponseDto> getAllTasksByProjectId(
            Long projectId) {

        Project project = getProjectById(projectId);

        return taskRepository.findAllByProjectId(project.getId())
                .stream()
                .map(taskMapper::toDto)
                .toList();
    }

    @Override
    public TaskResponseDto getTaskById(Long taskId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        if (user != null) {
            Task task = getExistTaskById(taskId);


            Long roleId = RoleName.ADMIN.ordinal() + 1L;
            Role role = roleRepository.findById(roleId).orElseThrow(
                    () -> new EntityNotFoundCustomException("Can not find role with id:" + roleId)
            );

            if (Objects.equals(user.getId(), task.getUser().getId())
                    || user.getRoles().contains(role)) {
                return taskMapper.toDto(task);
            } else {
                throw new UserCredentialException("No access to this resource");
            }
        } else {
            throw new UserCredentialException("Credential authentication error. User is null");
        }
    }

    @Override
    public List<TaskResponseDto> getAllTasksByUserId(Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        List<Task> tasks = taskRepository.findAllByUserId(user.getId());
        return tasks.stream()
                .map(taskMapper::toDto)
                .toList();
    }

    @Override
    public TaskResponseDto update(Long taskId, UpdateTaskRequireDto requireDto) {
        Task existTask = getExistTaskById(taskId);
        User user = userRepository.findById(requireDto.getUserId()).orElseThrow(
                () -> new EntityNotFoundCustomException("Can not find user with id:"
                        + requireDto.getUserId())
        );

        existTask.setName(requireDto.getName());
        existTask.setUser(user);
        existTask.setDescription(requireDto.getDescription());
        existTask.setPriority(requireDto.getPriority());
        existTask.setStatus(requireDto.getStatus());
        Set<Label> labels = getListLabelsByListLabelIds(requireDto.getLabelsIds());

        existTask.setLabels(labels);

        return taskMapper.toDto(taskRepository.save(existTask));
    }

    @Override
    public void deleteById(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    private User getExistUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundCustomException("Can not find user with id:" + userId)
        );
    }

    private Task getExistTaskById(Long taskId) {
        return taskRepository.findById(taskId).orElseThrow(
                () -> new EntityNotFoundCustomException("Can nor find task with id:" + taskId)
        );
    }

    private Set<Label> getListLabelsByListLabelIds(Set<Long> labels) {
        if (labels == null) {
            return Collections.emptySet();
        }
        return labels.stream()
                .map(labelRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }

    private Project getProjectById(Long projectId) {
        return projectRepository.findById(projectId).orElseThrow(
                () -> new EntityNotFoundCustomException("Can not find project with id:" + projectId)
        );
    }
}
