package org.globaroman.taskmanagementsystem.service;

import java.util.List;
import org.globaroman.taskmanagementsystem.dto.CreateLabelRequireDto;
import org.globaroman.taskmanagementsystem.dto.LabelResponseDto;

public interface LabelService {
    LabelResponseDto save(CreateLabelRequireDto requireDto);

    List<LabelResponseDto> getAll();

    LabelResponseDto update(Long id, CreateLabelRequireDto requireDto);

    void deletebyId(Long id);
}
