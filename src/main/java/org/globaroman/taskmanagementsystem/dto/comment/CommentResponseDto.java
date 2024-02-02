package org.globaroman.taskmanagementsystem.dto.comment;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CommentResponseDto {
    private Long id;
    private String text;
    private LocalDateTime dateAdd;
    private String userName;
}
