package org.globaroman.taskmanagementsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Comments", description = "Operations with comments")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    @Operation(summary = "Create a new comment")
    public CommentResponseDto create(@RequestBody CreateCommentRequireDto requireDto,
                                     Authentication authentication) {

        return commentService.create(requireDto, authentication);
    }

    @GetMapping("/{taskId}/comments")
    @Operation(summary = "Get all comments")
    public List<CommentResponseDto> getAllCommentsByTaskId(@PathVariable Long taskId) {
        return commentService.getAll(taskId);
    }

    @PatchMapping("/{commentId}")
    @Operation(summary = "Update an exist comment")
    public CommentResponseDto update(@PathVariable Long commentId,
                                     @RequestBody CreateCommentRequireDto requireDto,
                                     Authentication authentication) {

        return commentService.update(commentId, requireDto, authentication);
    }

    @DeleteMapping("/{commentId}")
    @Operation(summary = "Delete an exist comment")
    public void deleteByID(@PathVariable Long commentId, Authentication authentication) {
        commentService.delete(commentId, authentication);
    }
}
