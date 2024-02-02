package org.globaroman.taskmanagementsystem.dto.task;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.globaroman.taskmanagementsystem.dto.attachment.AttachmentResponseDto;
import org.globaroman.taskmanagementsystem.dto.comment.CommentResponseDto;
import org.globaroman.taskmanagementsystem.model.Priority;
import org.globaroman.taskmanagementsystem.model.Status;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponseDto {
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
