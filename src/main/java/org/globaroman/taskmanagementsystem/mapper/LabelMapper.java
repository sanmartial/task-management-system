package org.globaroman.taskmanagementsystem.mapper;

import org.globaroman.taskmanagementsystem.config.MapperConfig;
import org.globaroman.taskmanagementsystem.dto.label.CreateLabelRequireDto;
import org.globaroman.taskmanagementsystem.dto.label.LabelResponseDto;
import org.globaroman.taskmanagementsystem.model.Label;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface LabelMapper {

    Label toModel(CreateLabelRequireDto requireDto);

    LabelResponseDto toDto(Label save);
}
