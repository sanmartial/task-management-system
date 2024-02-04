package org.globaroman.taskmanagementsystem.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.globaroman.taskmanagementsystem.dto.comment.CommentResponseDto;
import org.globaroman.taskmanagementsystem.dto.comment.CreateCommentRequireDto;
import org.globaroman.taskmanagementsystem.exception.EntityNotFoundCustomException;
import org.globaroman.taskmanagementsystem.exception.UserCredentialException;
import org.globaroman.taskmanagementsystem.mapper.CommentMapper;
import org.globaroman.taskmanagementsystem.model.Comment;
import org.globaroman.taskmanagementsystem.model.Role;
import org.globaroman.taskmanagementsystem.model.RoleName;
import org.globaroman.taskmanagementsystem.model.Task;
import org.globaroman.taskmanagementsystem.model.User;
import org.globaroman.taskmanagementsystem.repository.CommentRepository;
import org.globaroman.taskmanagementsystem.repository.RoleRepository;
import org.globaroman.taskmanagementsystem.repository.TaskRepository;
import org.globaroman.taskmanagementsystem.service.CommentService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final TaskRepository taskRepository;
    private final RoleRepository roleRepository;

    @Override
    public CommentResponseDto create(CreateCommentRequireDto requireDto,
                                     Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Task task = getTaskByTaskId(requireDto.getTaskId());
        Comment comment = commentMapper.toModel(requireDto);
        comment.setDateAdd(LocalDateTime.now());
        comment.setTask(task);
        comment.setUser(user);

        return commentMapper.toDto(commentRepository.save(comment));
    }

    @Override
    public List<CommentResponseDto> getAll(Long taskId) {
        return commentRepository.findAllByTaskId(taskId)
                .stream()
                .map(commentMapper::toDto)
                .toList();
    }

    @Override
    public CommentResponseDto update(Long commentId,
                                     CreateCommentRequireDto requireDto,
                                     Authentication authentication) {
        Comment commentExist = getCommentById(commentId);
        User user = (User) authentication.getPrincipal();

        if (isExistAccess(user, commentExist)) {
            commentExist.setText(requireDto.getText());
            return commentMapper.toDto(commentRepository.save(commentExist));
        } else {
            throw new UserCredentialException("No access to this resource");
        }
    }

    @Override
    public void delete(Long commentId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Comment comment = getCommentById(commentId);
        if (isExistAccess(user, comment)) {
            commentRepository.deleteById(commentId);
        } else {
            throw new UserCredentialException("No access to this resource");
        }
    }

    private Task getTaskByTaskId(Long taskId) {
        return taskRepository.findById(taskId).orElseThrow(
                () -> new EntityNotFoundCustomException("Can not find task with id:" + taskId));
    }

    private Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                () -> new EntityNotFoundCustomException("Can not find comment with id:" + commentId)
        );
    }

    private boolean isExistAccess(User user, Comment comment) {
        Long roleId = RoleName.ADMIN.ordinal() + 1L;
        Role role = roleRepository.findById(roleId).orElseThrow(
                () -> new EntityNotFoundCustomException("Can not find role with id:" + roleId)
        );
        return Objects.equals(user.getId(), comment.getUser().getId())
                || user.getRoles().contains(role);
    }
}
