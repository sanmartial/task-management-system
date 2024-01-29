package org.globaroman.taskmanagementsystem.dto.project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProjectRequestDto {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @Positive
    private int durationInDay;
}
