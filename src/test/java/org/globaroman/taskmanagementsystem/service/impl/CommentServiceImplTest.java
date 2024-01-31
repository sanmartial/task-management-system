package org.globaroman.taskmanagementsystem.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.globaroman.taskmanagementsystem.dto.comment.CommentResponseDto;
import org.globaroman.taskmanagementsystem.dto.comment.CreateCommentRequireDto;
import org.globaroman.taskmanagementsystem.mapper.CommentMapper;
import org.globaroman.taskmanagementsystem.model.Comment;
import org.globaroman.taskmanagementsystem.model.Role;
import org.globaroman.taskmanagementsystem.model.RoleName;
import org.globaroman.taskmanagementsystem.model.Task;
import org.globaroman.taskmanagementsystem.model.User;
import org.globaroman.taskmanagementsystem.repository.CommentRepository;
import org.globaroman.taskmanagementsystem.repository.RoleRepository;
import org.globaroman.taskmanagementsystem.repository.TaskRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CommentMapper commentMapper;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    @Test
    @DisplayName("Save a new comment -> Should get CommentResponseDto successful result")
    void create_SaveComment_ShouldReturnCommentResponseDto() {
        Task task = new Task();
        task.setId(1L);
        User user = new User();
        user.setId(1L);
        task.setUser(user);

        CreateCommentRequireDto requireDto = new CreateCommentRequireDto();
        requireDto.setText("Test comment for test");
        requireDto.setTaskId(1L);

        Authentication authentication = Mockito.mock(Authentication.class);
        Comment comment = getCommentForTest(user, task);

        CommentResponseDto responseDto = getResponseDto();

        Mockito.when(authentication.getPrincipal()).thenReturn(user);
        Mockito.when(taskRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(task));
        Mockito.when(commentMapper.toModel(requireDto)).thenReturn(comment);
        Mockito.when(commentRepository.save(comment)).thenReturn(comment);
        Mockito.when(commentMapper.toDto(comment)).thenReturn(responseDto);

        CommentResponseDto result = commentService.create(requireDto, authentication);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(responseDto, result);
        Assertions.assertEquals(comment.getText(), result.getText());
    }

    @Test
    @DisplayName("Get all comments by TaskId -> "
            + "Should return list CommentsResponseDto")
    void getAll_GetAllCommentByTaskID_ShouldReturnListCommentResponseDto() {
        Task task = new Task();
        task.setId(1L);
        User user = new User();
        user.setId(1L);
        task.setUser(user);
        Comment comment = getCommentForTest(user, task);
        List<Comment> comments = new ArrayList<>();
        comments.add(comment);

        Mockito.when(commentRepository.findAllByTaskId(Mockito.anyLong())).thenReturn(comments);
        Mockito.when(commentMapper.toDto(comment)).thenReturn(getResponseDto());

        List<CommentResponseDto> results = commentService.getAll(1L);

        Assertions.assertNotNull(results);
        Assertions.assertEquals(1, results.size());
    }

    @Test
    @DisplayName("Update exist comment -> return CommentResponseDto")
    void update_UpdateValidComment_ReturnUpdatedCommentResponseDto() {
        Task task = new Task();
        task.setId(1L);
        User user = new User();
        user.setId(1L);
        task.setUser(user);

        CreateCommentRequireDto requireDto = new CreateCommentRequireDto();
        requireDto.setText("Test comment for test update");
        requireDto.setTaskId(1L);

        Comment comment = getCommentForTest(user, task);

        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getPrincipal()).thenReturn(user);

        Mockito.when(commentRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(comment));
        comment.setText(requireDto.getText());
        Mockito.when(commentRepository.save(comment)).thenReturn(comment);

        Role role = new Role(1L, RoleName.ADMIN);
        Mockito.when(roleRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(role));

        CommentResponseDto responseDto = getResponseDto();
        Mockito.when(commentMapper.toDto(comment)).thenReturn(responseDto);

        CommentResponseDto result = commentService.update(1L, requireDto, authentication);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(responseDto, result);
    }

    @Test
    @DisplayName("Delete exist comment -> Should successful result")
    void delete_DeleteExistingComment_ShouldSuccessDelete() {
        Task task = new Task();
        task.setId(1L);
        User user = new User();
        user.setId(1L);
        task.setUser(user);
        Comment comment = getCommentForTest(user,task);
        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getPrincipal()).thenReturn(user);
        Mockito.when(commentRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(comment));
        Role role = new Role(1L, RoleName.ADMIN);
        Mockito.when(roleRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(role));

        commentService.delete(comment.getId(), authentication);

        Mockito.verify(commentRepository, Mockito.times(1)).deleteById(comment.getId());
    }

    private Comment getCommentForTest(User user, Task task) {
        Comment comment = new Comment();
        comment.setDateAdd(LocalDateTime.now());
        comment.setTask(task);
        comment.setUser(user);
        comment.setText("Test comment for test");
        comment.setId(1L);
        return comment;
    }

    private CommentResponseDto getResponseDto() {
        CommentResponseDto responseDto = new CommentResponseDto();
        responseDto.setUserName("User");
        responseDto.setId(1L);
        responseDto.setText("Test comment for test");
        responseDto.setDateAdd(LocalDateTime.now());
        return responseDto;
    }
}
