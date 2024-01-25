package org.globaroman.taskmanagementsystem.dto.label;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.globaroman.taskmanagementsystem.model.Color;

@Data
public class CreateLabelRequireDto {
    @NotBlank
    private String name;
    @NotBlank
    private Color color;
}
