package org.globaroman.taskmanagementsystem.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.globaroman.taskmanagementsystem.dto.attachment.AttachmentRespopnseDto;
import org.globaroman.taskmanagementsystem.service.AttachmentService;
import org.globaroman.taskmanagementsystem.dto.attachment.CreateAttachmentRequireDto;
import org.springframework.core.io.InputStreamResource;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/attachments")
public class AttachmentController {

    private final AttachmentService attachmentService;

    @PostMapping("/{taskId}")
    public AttachmentRespopnseDto create(
            @RequestBody CreateAttachmentRequireDto requireDto,
            @PathVariable Long taskId,
            Authentication authentication) {
        return attachmentService.create(requireDto, taskId, authentication);
    }

    @GetMapping("/{taskId}")
    public List<AttachmentRespopnseDto> getAttachmentByTaskId(@PathVariable Long taskId) {
        return attachmentService.getAllAttachmentsByTaskId(taskId);
    }

    @GetMapping("/task/{attachmentId}")
    public InputStreamResource getAttachmentById(@PathVariable Long attachmentId) {
        return attachmentService.getAttachmentById(attachmentId);
    }
}
