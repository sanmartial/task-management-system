package org.globaroman.taskmanagementsystem.dto.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.globaroman.taskmanagementsystem.model.Priority;
import org.globaroman.taskmanagementsystem.model.Status;
import java.util.Set;

@Data
public class UpdateTaskRequireDto {
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
    private Status status;
    @NotNull
    private Set<Long> labelsIds;
}
