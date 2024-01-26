package org.globaroman.taskmanagementsystem.service;

import java.util.List;
import org.globaroman.taskmanagementsystem.dto.attachment.AttachmentRespopnseDto;
import org.globaroman.taskmanagementsystem.dto.attachment.CreateAttachmentRequireDto;
import org.springframework.core.io.InputStreamResource;
import org.springframework.security.core.Authentication;

public interface AttachmentService {
    AttachmentRespopnseDto create(CreateAttachmentRequireDto requireDto,
                                  Long taskId, Authentication authentication);

    List<AttachmentRespopnseDto> getAllAttachmentsByTaskId(Long taskId);

    InputStreamResource getAttachmentById(Long attachmentId);
}
