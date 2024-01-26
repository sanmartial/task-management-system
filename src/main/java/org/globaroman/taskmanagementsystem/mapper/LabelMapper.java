package org.globaroman.taskmanagementsystem.mapper;

import org.globaroman.taskmanagementsystem.config.MapperConfig;
import org.globaroman.taskmanagementsystem.dto.label.CreateLabelRequireDto;
import org.globaroman.taskmanagementsystem.dto.label.LabelResponseDto;
import org.globaroman.taskmanagementsystem.model.Label;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = MapperConfig.class)
public interface LabelMapper {

    Label toModel(CreateLabelRequireDto requireDto);

    LabelResponseDto toDto(Label save);

    Label toEntity(Label label);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Label partialUpdate(CreateLabelRequireDto requireDto, @MappingTarget Label label);
}
