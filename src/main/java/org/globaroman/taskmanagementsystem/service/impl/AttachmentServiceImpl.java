package org.globaroman.taskmanagementsystem.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.globaroman.taskmanagementsystem.dto.attachment.AttachmentResponseDto;
import org.globaroman.taskmanagementsystem.exception.EntityNotFoundCustomException;
import org.globaroman.taskmanagementsystem.mapper.AttachmentMapper;
import org.globaroman.taskmanagementsystem.model.Attachment;
import org.globaroman.taskmanagementsystem.model.Task;
import org.globaroman.taskmanagementsystem.model.User;
import org.globaroman.taskmanagementsystem.repository.AttachmentRepository;
import org.globaroman.taskmanagementsystem.repository.TaskRepository;
import org.globaroman.taskmanagementsystem.service.AttachmentService;
import org.globaroman.taskmanagementsystem.dto.attachment.CreateAttachmentRequireDto;
import org.globaroman.taskmanagementsystem.service.DropBoxService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository attachmentRepository;

    private final TaskRepository taskRepository;

    private final AttachmentMapper attachmentMapper;

    private final DropBoxService dropBoxService;

    @Override
    public AttachmentResponseDto create(CreateAttachmentRequireDto requireDto,
                                         Authentication authentication) {
        Task task = getTaskFromDataBaseByTaskId(requireDto.getTaskId());
        User user = (User) authentication.getPrincipal();
        Attachment attachment = new Attachment();
        attachment.setTask(task);
        attachment.setUser(user);
        attachment.setFileName(requireDto.getFileName());
        attachment.setUploadDate(LocalDateTime.now());
        attachment.setDropBoxId(
                dropBoxService.getDropBoxIdFromMetadataUploadFile(requireDto.getFilePath()));

        Attachment saved = attachmentRepository.save(attachment);
        List<Attachment> attachmentList = new ArrayList<>();
        attachmentList.add(saved);
        task.setAttachments(attachmentList);
        taskRepository.save(task);

        return attachmentMapper.toDto(saved);
    }

    @Override
    public List<AttachmentResponseDto> getAllAttachmentsByTaskId(Long taskId) {

        return attachmentRepository.findByTaskId(taskId).stream()
                .map(attachmentMapper::toDto)
                .toList();
    }

    @Override
    public InputStreamResource getAttachmentById(Long attachmentId) {
        Attachment attachment = attachmentRepository.findById(attachmentId).orElseThrow(
                () -> new EntityNotFoundCustomException("Cannot find attachment by id:"
                        + attachmentId)
        );
        return new InputStreamResource(dropBoxService.downloadFileFromDropBoxById(attachment));
    }

    private Task getTaskFromDataBaseByTaskId(Long taskId) {
        return taskRepository.findById(taskId).orElseThrow(
                () -> new EntityNotFoundCustomException("Can not find tsk by id:" + taskId)
        );
    }
}
