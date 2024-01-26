package org.globaroman.taskmanagementsystem.dto.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.globaroman.taskmanagementsystem.model.Priority;
import org.globaroman.taskmanagementsystem.model.Task;

import java.io.Serializable;
import java.util.Set;

@Value
public class CreateTaskRequireDto implements Serializable {
    @NotBlank
    String name;
    @NotBlank
    String description;
    @NotNull
    Priority priority;
    @NotNull
    Set<Long> labelsIds;
}
