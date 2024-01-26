package org.globaroman.taskmanagementsystem.dto.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.globaroman.taskmanagementsystem.model.Priority;
import java.io.Serializable;
import java.util.Set;

@Value
public class CreateTaskRequireDto implements Serializable {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private Priority priority;
    @NotNull
    private Set<Long> labelsIds;
}
