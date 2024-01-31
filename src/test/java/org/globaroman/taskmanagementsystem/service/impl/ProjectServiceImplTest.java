package org.globaroman.taskmanagementsystem.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.globaroman.taskmanagementsystem.dto.project.CreateProjectRequestDto;
import org.globaroman.taskmanagementsystem.dto.project.ProjectResponseDto;
import org.globaroman.taskmanagementsystem.dto.task.TaskResponseDto;
import org.globaroman.taskmanagementsystem.mapper.ProjectMapper;
import org.globaroman.taskmanagementsystem.mapper.TaskMapper;
import org.globaroman.taskmanagementsystem.model.Attachment;
import org.globaroman.taskmanagementsystem.model.Comment;
import org.globaroman.taskmanagementsystem.model.Label;
import org.globaroman.taskmanagementsystem.model.Priority;
import org.globaroman.taskmanagementsystem.model.Project;
import org.globaroman.taskmanagementsystem.model.Status;
import org.globaroman.taskmanagementsystem.model.Task;
import org.globaroman.taskmanagementsystem.model.User;
import org.globaroman.taskmanagementsystem.repository.ProjectRepository;
import org.globaroman.taskmanagementsystem.repository.TaskRepository;
import org.globaroman.taskmanagementsystem.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectMapper projectMapper;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ProjectServiceImpl projectService;

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Save a new project -> Should get ProjectResponseDto successful result")
    void create_SaveProject_ShouldReturnProjectResponseDto_SuccessfulResult() {
        User user = new User();
        user.setId(1L);
        Authentication authentication = Mockito.mock(Authentication.class);
        Task task = getTaskForTest(user);
        Project project = getProjectForTest(task, user);

        Mockito.when(authentication.getPrincipal()).thenReturn(user);
        Mockito.when(projectRepository.save(Mockito.any(Project.class))).thenReturn(project);

        ProjectResponseDto responseDto = getProjectResponseDto();
        Mockito.when(projectMapper.toDto(project)).thenReturn(responseDto);

        CreateProjectRequestDto requestDto = getCreateRequestDto();
        ProjectResponseDto result = projectService.create(requestDto, authentication);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(responseDto, result);
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Get all projects -> Should get List<ProjectResponseDto> -> successful result")
    void getAll_GetAllProject_ShouldReturnListProjectResponseDto_SuccessfulResult() {
        User user = new User();
        user.setId(1L);
        Authentication authentication = Mockito.mock(Authentication.class);
        Task task = getTaskForTest(user);
        Project project = getProjectForTest(task, user);
        List<Project> projects = new ArrayList<>();
        projects.add(project);
        ProjectResponseDto responseDto = getProjectResponseDto();

        Mockito.when(projectRepository.findAll()).thenReturn(projects);
        Mockito.when(projectMapper.toDto(project)).thenReturn(responseDto);

        List<ProjectResponseDto> responseDtos = new ArrayList<>();
        responseDtos.add(responseDto);

        List<ProjectResponseDto> results = projectService.getAll();

        Assertions.assertNotNull(results);
        Assertions.assertEquals(1, results.size());
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Get projects by id -> Should received ProjectResponseDto -> successful result")
    void getProjectById_ShouldReceivedProjectResponseDto_SuccessfulResult() {
        User user = new User();
        user.setId(1L);
        Authentication authentication = Mockito.mock(Authentication.class);
        Task task = getTaskForTest(user);
        Project project = getProjectForTest(task, user);
        ProjectResponseDto responseDto = getProjectResponseDto();

        Mockito.when(projectRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(project));
        Mockito.when(projectMapper.toDto(project)).thenReturn(responseDto);

        ProjectResponseDto result = projectService.getProjectById(1L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(responseDto, result);
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Update exist project -> Should received ProjectResponseDto -> successful result")
    void update_UpdateExistProject_SuccessfulResult() {
        User user = new User();
        user.setId(1L);
        Authentication authentication = Mockito.mock(Authentication.class);
        Task task = getTaskForTest(user);
        Project project = getProjectForTest(task, user);
        CreateProjectRequestDto requestDto = getCreateRequestDto();
        requestDto.setDurationInDay(20);

        Mockito.when(projectRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(project));

        project.setEndDate(project.getStartDate().plusDays(requestDto.getDurationInDay()));

        Mockito.when(projectRepository.save(project)).thenReturn(project);

        ProjectResponseDto responseDto = getProjectResponseDto();

        Mockito.when(projectMapper.toDto(project)).thenReturn(responseDto);

        ProjectResponseDto result = projectService.update(1L, requestDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(responseDto, result);
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Delete exist project by id -> Should delete successful result")
    void deleteById_DeleteExistProject_SuccessfulResult() {
        User user = new User();
        user.setId(1L);
        Authentication authentication = Mockito.mock(Authentication.class);
        Task task = getTaskForTest(user);
        Project project = getProjectForTest(task, user);

        projectRepository.deleteById(project.getId());

        Mockito.verify(projectRepository, Mockito.times(1)).deleteById(project.getId());

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

    private CreateProjectRequestDto getCreateRequestDto() {
        CreateProjectRequestDto requestDto = new CreateProjectRequestDto();
        requestDto.setDescription("Description project");
        requestDto.setName("Name project");
        requestDto.setDurationInDay(10);

        return requestDto;
    }

    private Project getProjectForTest(Task task, User user) {
        Project project = new Project();
        project.setId(1L);
        project.setName("Name project");
        project.setDescription("Description project");
        project.setStartDate(LocalDateTime.now());
        project.setEndDate(LocalDateTime.now().plusDays(10));
        project.setStatus(Status.NOT_STARTED);
        project.setUser(user);
        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
        project.setTasks(tasks);
        return project;
    }

    private ProjectResponseDto getProjectResponseDto() {
        ProjectResponseDto project = new ProjectResponseDto();
        project.setId(1L);
        project.setName("Name project");
        project.setDescription("Description project");
        project.setStartDate(LocalDate.now());
        project.setEndDate(LocalDate.now().plusDays(10));
        project.setStatus(Status.NOT_STARTED);
        TaskResponseDto task = new TaskResponseDto();
        task.setId(1L);
        List<TaskResponseDto> tasks = new ArrayList<>();
        tasks.add(task);
        project.setTasks(tasks);
        return project;
    }
}
