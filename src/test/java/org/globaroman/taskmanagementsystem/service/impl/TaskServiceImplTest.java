package org.globaroman.taskmanagementsystem.service.impl;

import liquibase.pro.packaged.L;
import org.globaroman.taskmanagementsystem.dto.attachment.AttachmentResponseDto;
import org.globaroman.taskmanagementsystem.dto.comment.CommentResponseDto;
import org.globaroman.taskmanagementsystem.dto.label.CreateLabelRequireDto;
import org.globaroman.taskmanagementsystem.dto.task.CreateTaskRequireDto;
import org.globaroman.taskmanagementsystem.dto.task.TaskResponseDto;
import org.globaroman.taskmanagementsystem.dto.task.UpdateTaskRequireDto;
import org.globaroman.taskmanagementsystem.mapper.LabelMapper;
import org.globaroman.taskmanagementsystem.mapper.TaskMapper;
import org.globaroman.taskmanagementsystem.model.Attachment;
import org.globaroman.taskmanagementsystem.model.Color;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.testcontainers.shaded.org.bouncycastle.pqc.jcajce.provider.LMS;

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
    private LabelMapper labelMapper;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Save a new task -> Should get TaskResponseDto successful result")
    void create_SaveTask_ShouldReturnTaskResponseDto_SuccessfulResult() {
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
    @WithMockUser(username = "admin", roles = {"ADMIN"})
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
    void getTaskById_ShouldReturnTaskResponseDto_SuccessfulResult() {
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
    @DisplayName("Get exist task by userId-> Should get List TaskResponseDto successful result")
    void getAllTasksByUserId_ShouldReturnListTaskResponseDto_SuccessfulResult() {
        User user = new User();
        user.setId(1L);
        Authentication authentication = Mockito.mock(Authentication.class);

        Mockito.when(authentication.getPrincipal()).thenReturn(user);

        Task task = getTaskForTest(user);

        Project project = new Project();
        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
        Task task2 = getTaskForTest(user);
        tasks.add(task2);
        project.setTasks(tasks);
        project.setId(1L);

        Mockito.when(taskRepository.findAllByUserId(Mockito.anyLong())).thenReturn(tasks);
        Mockito.when(taskMapper.toDto(task)).thenReturn(getResponseDto());
        List<TaskResponseDto> results = taskService.getAllTasksByUserId(authentication);

        Assertions.assertNotNull(results);
        Assertions.assertEquals(2, results.size());
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Update the exist task -> Should return TaskResponseDto successful result")
    void update_ShouldReturnTaskResponseDto_SuccessfulResult() {
        User user = new User();
        user.setId(1L);
        Task task = getTaskForTest(user);
        UpdateTaskRequireDto requireDto = new UpdateTaskRequireDto();
        requireDto.setDescription("New Description");
        requireDto.setName("New name");
        Label label = new Label();
        label.setId(1L);
        label.setColor(Color.RED);
        Set<Label> labels = new HashSet<>();
        labels.add(label);
        task.setLabels(labels);

        Mockito.when(taskRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(task));
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));

        task.setName("New name");
        task.setDescription("New Description");

        Mockito.when(taskRepository.save(Mockito.any(Task.class))).thenReturn(task);

        TaskResponseDto responseDto = getResponseDto();
        responseDto.setName("New name");
        responseDto.setDescription("New Description");
        Mockito.when(taskMapper.toDto(task)).thenReturn(responseDto);

        TaskResponseDto result = taskService.update(1L, requireDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(responseDto, result);
        Assertions.assertEquals(requireDto.getName(), result.getName());
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Delete exist task -> Should successful result")
    void deleteById_DeleteExistTask_SuccessfulResult() {
        User user = new User();
        user.setId(1L);
        Task task = getTaskForTest(user);

        taskService.deleteById(task.getId());

        Mockito.verify(taskRepository, Mockito.times(1)).deleteById(task.getId());
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