package org.globaroman.taskmanagementsystem.controller;

import org.globaroman.taskmanagementsystem.mapper.CommentMapper;
import org.globaroman.taskmanagementsystem.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;


class CommentControllerTest {

        @BeforeEach
    void setUp() {
    }

    @Test
    void create_SaveComment_ShouldReturnCommentResponseDtoEndStatusCreated() {
    }

    @Test
    void getAllByTaskId_GetAllCommentByTaskID_ShouldReturnListCommentResponseDtoEndStatusOk() {
    }

    @Test
    void update_UpdateValidComment_ReturnUpdatedCommentResponseDtoEndStatusOk() {
    }

    @Test
    void deleteByID_DeleteExistingComment_ShouldSuccessDeleteEndStatusOk() {
    }
}