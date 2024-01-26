package org.globaroman.taskmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.globaroman.taskmanagementsystem.dto.attachment.AttachmentResponseDto;
import org.globaroman.taskmanagementsystem.dto.attachment.CreateAttachmentRequireDto;
import org.globaroman.taskmanagementsystem.service.AttachmentService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/attachments")
public class AttachmentController {

    private final AttachmentService attachmentService;

    @PostMapping("/{taskId}")
    @ResponseStatus(HttpStatus.CREATED)
    public AttachmentResponseDto create(
            @RequestBody CreateAttachmentRequireDto requireDto,
            @PathVariable Long taskId,
            Authentication authentication) {
        return attachmentService.create(requireDto, taskId, authentication);
    }

    @GetMapping("/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    public List<AttachmentResponseDto> getAttachmentByTaskId(@PathVariable Long taskId) {
        return attachmentService.getAllAttachmentsByTaskId(taskId);
    }

    @GetMapping("/task/{attachmentId}")
    @ResponseStatus(HttpStatus.OK)
    public InputStreamResource getAttachmentById(@PathVariable Long attachmentId) {
        return attachmentService.getAttachmentById(attachmentId);
    }
}
