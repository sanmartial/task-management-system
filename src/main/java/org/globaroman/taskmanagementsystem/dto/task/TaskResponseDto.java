package org.globaroman.taskmanagementsystem.dto.task;

import lombok.Data;
import org.globaroman.taskmanagementsystem.dto.attachment.AttachmentRespopnseDto;
import org.globaroman.taskmanagementsystem.model.Attachment;
import org.globaroman.taskmanagementsystem.model.Priority;
import org.globaroman.taskmanagementsystem.model.Status;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;


@Data
public class TaskResponseDto implements Serializable {
    Long id;
    String name;
    String description;
    Priority priority;
    Status status;
    LocalDate date;
    List<AttachmentRespopnseDto> attachments;
    Set<Long> labelsIds;
}
