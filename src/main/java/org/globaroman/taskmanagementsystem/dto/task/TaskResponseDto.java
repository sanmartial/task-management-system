package org.globaroman.taskmanagementsystem.dto.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.globaroman.taskmanagementsystem.dto.attachment.AttachmentResponseDto;
import org.globaroman.taskmanagementsystem.dto.comment.CommentResponseDto;
import org.globaroman.taskmanagementsystem.model.Priority;
import org.globaroman.taskmanagementsystem.model.Status;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponseDto implements Serializable {
    private Long id;
    private String name;
    private String description;
    private Priority priority;
    private Status status;
    private LocalDate date;
    private List<AttachmentResponseDto> attachments;
    private List<CommentResponseDto> comments;
    private Set<Long> labelsIds;
}
