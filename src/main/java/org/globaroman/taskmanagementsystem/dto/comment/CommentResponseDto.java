package org.globaroman.taskmanagementsystem.dto.comment;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CommentResponseDto {
    private Long id;
    private String text;
    private LocalDateTime dateAdd;
    private String userName;
}
