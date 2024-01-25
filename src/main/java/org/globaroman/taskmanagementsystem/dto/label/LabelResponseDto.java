package org.globaroman.taskmanagementsystem.dto.label;

import lombok.Data;
import org.globaroman.taskmanagementsystem.model.Color;

@Data
public class LabelResponseDto {
    private Long id;
    private String name;
    private Color color;
}
