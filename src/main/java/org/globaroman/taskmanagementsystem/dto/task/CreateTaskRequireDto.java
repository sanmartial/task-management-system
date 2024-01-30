package org.globaroman.taskmanagementsystem.dto.task;

import java.util.Set;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.globaroman.taskmanagementsystem.model.Priority;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskRequireDto {
    @NotNull
    private Long projectId;
    @NotNull
    private Long userId;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private Priority priority;
    @NotNull
    private Set<Long> labelsIds;
}
