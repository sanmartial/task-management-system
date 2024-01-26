package org.globaroman.taskmanagementsystem.dto.project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Value;

import java.io.Serializable;

@Value
public class CreateProjectRequestDto implements Serializable {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @Positive
    private int durationInDay;
}