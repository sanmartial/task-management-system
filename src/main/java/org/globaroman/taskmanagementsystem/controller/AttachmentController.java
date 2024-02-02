package org.globaroman.taskmanagementsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
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

@Tag(name = "Attachments", description = "Operations with attachments")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/attachments")
public class AttachmentController {

    private final AttachmentService attachmentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new attachment")
    public AttachmentResponseDto create(
            @RequestBody CreateAttachmentRequireDto requireDto,
            Authentication authentication) {
        return attachmentService.create(requireDto, authentication);
    }

    @GetMapping("/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all attachments")
    public List<AttachmentResponseDto> getAttachmentByTaskId(@PathVariable Long taskId) {
        return attachmentService.getAllAttachmentsByTaskId(taskId);
    }

    @GetMapping("/task/{attachmentId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get attachment by id")
    public InputStreamResource getAttachmentById(@PathVariable Long attachmentId) {
        return attachmentService.getAttachmentById(attachmentId);
    }
}
