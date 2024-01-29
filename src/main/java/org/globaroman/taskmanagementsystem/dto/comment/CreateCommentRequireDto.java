package org.globaroman.taskmanagementsystem.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.globaroman.taskmanagementsystem.model.Comment;


/**
 * DTO for {@link Comment}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCommentRequireDto {
    @NotBlank
    private String text;
    @NotNull
    private Long taskId;
}
