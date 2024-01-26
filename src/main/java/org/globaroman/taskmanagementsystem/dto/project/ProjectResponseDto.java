package org.globaroman.taskmanagementsystem.dto.project;

import lombok.Value;
import org.globaroman.taskmanagementsystem.model.Project;
import org.globaroman.taskmanagementsystem.model.Status;
import org.globaroman.taskmanagementsystem.model.Task;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link Project}
 */
@Value
public class ProjectResponseDto implements Serializable {
    Long id;
    String name;
    String description;
    LocalDate startDate;
    LocalDate endDate;
    Status status;
    List<Task> tasks;
}