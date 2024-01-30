package org.globaroman.taskmanagementsystem.service;

import java.util.List;
import org.globaroman.taskmanagementsystem.dto.comment.CommentResponseDto;
import org.globaroman.taskmanagementsystem.dto.comment.CreateCommentRequireDto;
import org.springframework.security.core.Authentication;

public interface CommentService {
    CommentResponseDto create(CreateCommentRequireDto requireDto, Authentication authentication);

    List<CommentResponseDto> getAll(Long taskId);

    CommentResponseDto update(Long commentId,
                              CreateCommentRequireDto requireDto,
                              Authentication authentication);

    void delete(Long commentId, Authentication authentication);
}
