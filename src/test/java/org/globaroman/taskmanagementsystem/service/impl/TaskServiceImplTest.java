package org.globaroman.taskmanagementsystem.service.impl;

import liquibase.pro.packaged.L;
import org.globaroman.taskmanagementsystem.dto.attachment.AttachmentResponseDto;
import org.globaroman.taskmanagementsystem.dto.comment.CommentResponseDto;
import org.globaroman.taskmanagementsystem.dto.task.CreateTaskRequireDto;
import org.globaroman.taskmanagementsystem.dto.task.TaskResponseDto;
import org.globaroman.taskmanagementsystem.mapper.TaskMapper;
import org.globaroman.taskmanagementsystem.model.Attachment;
import org.globaroman.taskmanagementsystem.model.Comment;
import org.globaroman.taskmanagementsystem.model.Label;
import org.globaroman.taskmanagementsystem.model.Priority;
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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private LabelRepository labelRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    @DisplayName("Save a new task -> Should get TaskResponseDto successful result")
    void create_SaveTask_ShouldReturnTaskResponseDto() {
        User user = new User();
        user.setId(1L);

        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));

        Task task = getTaskForTest(user);
        Mockito.when(taskRepository.save(Mockito.any(Task.class))).thenReturn(task);

        Project project = new Project();
        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
         project.setTasks(tasks);
        project.setId(1L);

        Set<Label> labels = new HashSet<>();
        labels.add(new Label());

        Mockito.when(projectRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(project));

        Mockito.when(projectRepository.save(project)).thenReturn(project);
        Mockito.when(taskRepository.save(task)).thenReturn(task);

        TaskResponseDto responseDto = getResponseDto();

        Mockito.when(taskMapper.toDto(task)).thenReturn(responseDto);

        CreateTaskRequireDto requireDto = getCreateTaskRequireDto();

        TaskResponseDto result = taskService.create(requireDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(responseDto, result);
    }

    @Test
    @DisplayName("Get All tasks -> Should get List<TaskResponseDto> -> successful result")
    void getAllTasksByProjectId_GetAllTask_ShouldReturnListTaskResponseDto() {
        User user = new User();
        user.setId(1L);
        Task task = getTaskForTest(user);

        Project project = new Project();
        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
        project.setTasks(tasks);
        project.setId(1L);

        Set<Label> labels = new HashSet<>();
        labels.add(new Label());

        Mockito.when(projectRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(project));
        Mockito.when(taskRepository.findAllByProjectId(Mockito.anyLong())).thenReturn(tasks);

        List<TaskResponseDto> results = taskService.getAllTasksByProjectId(1L);

        Assertions.assertNotNull(results);
        Assertions.assertEquals(1, results.size());
    }

    @Test
    @DisplayName("Get exist task by taskId-> Should get TaskResponseDto successful result")
    void getTaskById_ShouldReturnTaskResponseDto() {
        User user = new User();
        user.setId(1L);
        Authentication authentication = Mockito.mock(Authentication.class);

        Mockito.when(authentication.getPrincipal()).thenReturn(user);

        Role role = new Role(1L, RoleName.ADMIN);
        Mockito.when(roleRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(role));

        Task task = getTaskForTest(user);
        task.setUser(user);

        TaskResponseDto responseDto = getResponseDto();

        Mockito.when(taskRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(task));
        Mockito.when(taskMapper.toDto(task)).thenReturn(responseDto);

        TaskResponseDto result = taskService.getTaskById(1L, authentication);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(responseDto, result);
        Assertions.assertEquals(task.getName(), result.getName());
    }


    @Test
    void getAllTasksByUserId_ShouldReturnListTaskResponseDto() {
    }

    @Test
    void update_ShouldReturnTaskResponseDto() {
    }

    @Test
    void deleteById() {

    }

    private Task getTaskForTest(User user) {
        Task task = new Task();
        task.setId(1L);
        task.setName("Task 1");
        task.setDescription("Description 1");
        task.setPriority(Priority.MEDIUM);
        task.setStatus(Status.NOT_STARTED);
        task.setDate(LocalDateTime.now());
        task.setAttachments(List.of(new Attachment()));
        task.setComments(List.of(new Comment()));
        task.setLabels(Set.of(new Label()));
        return task;
    }

    private CreateTaskRequireDto getCreateTaskRequireDto() {
        CreateTaskRequireDto task = new CreateTaskRequireDto();
        task.setProjectId(1L);
        task.setUserId(1L);
        task.setName("Task 1");
        task.setDescription("Description 1");
        task.setPriority(Priority.MEDIUM);
        task.setLabelsIds(Set.of(1L));
        return task;
    }

    private TaskResponseDto getResponseDto() {
        TaskResponseDto task = new TaskResponseDto();
        task.setId(1L);
        task.setName("Task 1");
        task.setDescription("Description 1");
        task.setPriority(Priority.MEDIUM);
        task.setStatus(Status.NOT_STARTED);
        task.setDate(LocalDate.now());
        task.setAttachments(List.of(new AttachmentResponseDto()));
        task.setComments(List.of(new CommentResponseDto()));
        task.setLabelsIds(Set.of(1L));
        return task;
    }
}