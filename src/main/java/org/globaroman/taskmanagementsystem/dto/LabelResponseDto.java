package org.globaroman.taskmanagementsystem.dto;

import lombok.Data;
import org.globaroman.taskmanagementsystem.model.Color;

@Data
public class LabelResponseDto {
    private Long id;
    private String name;
    private Color color;
}
