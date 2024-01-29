package org.globaroman.taskmanagementsystem.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.globaroman.taskmanagementsystem.dto.comment.CommentResponseDto;
import org.globaroman.taskmanagementsystem.dto.comment.CreateCommentRequireDto;
import org.globaroman.taskmanagementsystem.service.CommentService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public CommentResponseDto create(@RequestBody CreateCommentRequireDto requireDto,
                                     Authentication authentication) {
        return commentService.create(requireDto, authentication);
    }

    @GetMapping("/{taskId}")
    public List<CommentResponseDto> getAllByTaskId(@PathVariable Long taskId,
                                                   Authentication authentication) {
        return commentService.getAll(taskId, authentication);
    }

    @PatchMapping("/{commentId}")
    public CommentResponseDto update(@PathVariable Long commentId,
                                     @RequestBody CreateCommentRequireDto requireDto,
                                     Authentication authentication) {
        return commentService.update(commentId, requireDto, authentication);
    }

    @DeleteMapping("/{commentId}")
    public void deleteByID(@PathVariable Long commentId, Authentication authentication) {
        commentService.delete(commentId, authentication);
    }
}
