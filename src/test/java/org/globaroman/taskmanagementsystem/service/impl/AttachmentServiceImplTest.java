package org.globaroman.taskmanagementsystem.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.globaroman.taskmanagementsystem.dto.attachment.AttachmentResponseDto;
import org.globaroman.taskmanagementsystem.dto.attachment.CreateAttachmentRequireDto;
import org.globaroman.taskmanagementsystem.mapper.AttachmentMapper;
import org.globaroman.taskmanagementsystem.model.Attachment;
import org.globaroman.taskmanagementsystem.model.Task;
import org.globaroman.taskmanagementsystem.model.User;
import org.globaroman.taskmanagementsystem.repository.AttachmentRepository;
import org.globaroman.taskmanagementsystem.repository.TaskRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.InputStreamResource;
import org.springframework.security.core.Authentication;

@ExtendWith(MockitoExtension.class)
class AttachmentServiceImplTest {

    @Mock
    private AttachmentRepository attachmentRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private AttachmentMapper attachmentMapper;

    @Mock
    private DropBoxServiceImpl dropboxService;

    @InjectMocks
    private AttachmentServiceImpl attachmentService;

    @Test
    @DisplayName("Save a new attachment -> Should get AttachmentResponseDto successful result")
    void create_SaveAttachment_ShouldReturnAttachmentResponseDto() {
        Task task = new Task();
        task.setId(1L);
        task.setName("Task name");
        User user = new User();
        user.setId(1L);
        task.setUser(user);
        Attachment attachment = createAttachmentAsTest();
        attachment.setUser(user);
        task.setAttachments(List.of(attachment));

        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getPrincipal()).thenReturn(user);

        AttachmentResponseDto responseDto = creareRespponseDto();
        CreateAttachmentRequireDto requireDto = createRequierDto();

        Mockito.when(authentication.getPrincipal()).thenReturn(user);
        Mockito.when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        Mockito.when(attachmentMapper.toEntity(requireDto, task, user)).thenReturn(attachment);
        Mockito.when(attachmentRepository.save(Mockito.any(Attachment.class)))
                .thenReturn(attachment);
        Mockito.when(attachmentMapper.toDto(attachment)).thenReturn(responseDto);
        Mockito.when(dropboxService.getDropBoxIdFromMetadataUploadFile(
                        task.getName(),
                        requireDto.getFilePath()))
                .thenReturn(attachment.getDropBoxId());

        AttachmentResponseDto result = attachmentService.create(requireDto, authentication);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(responseDto, result);
    }

    @Test
    @DisplayName("Get all attachments by TaskId -> "
            + "Should return list AttachmentResponseDtok")
    void getAllAttachmentsByTaskId_ShouldReturnListAttachmentResponseDto() {
        Attachment attachment = createAttachmentAsTest();
        List<Attachment> attachments = new ArrayList<>();
        attachments.add(attachment);
        AttachmentResponseDto respopnseDto = creareRespponseDto();
        List<AttachmentResponseDto> responseDtos = new ArrayList<>();
        responseDtos.add(respopnseDto);

        Mockito.when(attachmentRepository.findByTaskId(Mockito.anyLong())).thenReturn(attachments);
        Mockito.when(attachmentMapper.toDto(Mockito.any(Attachment.class)))
                .thenReturn(new AttachmentResponseDto());

        List<AttachmentResponseDto> results = attachmentService.getAllAttachmentsByTaskId(1L);

        Assertions.assertNotNull(results);
        Assertions.assertEquals(1, results.size());
    }

    @Test
    @DisplayName("Get attachment by attachmentId -> "
            + "Should return AttachmentResponseDto")
    void getAttachmentById_ShouldReturnAttachmentResponseDto() {
        Attachment attachment = createAttachmentAsTest();
        InputStream inputStream = new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        };

        Mockito.when(attachmentRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(attachment));
        Mockito.when(dropboxService.downloadFileFromDropBoxById(attachment))
                .thenReturn(inputStream);

        InputStreamResource result = attachmentService.getAttachmentById(1L);
        Mockito.verify(attachmentRepository).findById(1L);
        Mockito.verify(dropboxService).downloadFileFromDropBoxById(attachment);
    }

    private CreateAttachmentRequireDto createRequierDto() {
        CreateAttachmentRequireDto requireDto =
                new CreateAttachmentRequireDto();
        requireDto.setFileName("File A");
        requireDto.setFilePath("/File.txt");
        requireDto.setTaskId(1L);
        return requireDto;
    }

    private AttachmentResponseDto creareRespponseDto() {
        AttachmentResponseDto responseDto = new AttachmentResponseDto();
        responseDto.setId(1L);
        responseDto.setFileName("File A");
        responseDto.setUploadDate(LocalDate.now());
        responseDto.setDropBoxId("dRBxId");
        return responseDto;
    }

    private Attachment createAttachmentAsTest() {
        Attachment attachment = new Attachment();
        attachment.setDropBoxId("dRBxId");
        attachment.setUploadDate(LocalDateTime.now());
        attachment.setFileName("File A");
        return attachment;
    }
}
