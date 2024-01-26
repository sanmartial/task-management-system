package org.globaroman.taskmanagementsystem.mapper;

import org.globaroman.taskmanagementsystem.config.MapperConfig;
import org.globaroman.taskmanagementsystem.model.Attachment;
import org.globaroman.taskmanagementsystem.dto.attachment.AttachmentRespopnseDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = MapperConfig.class)
public interface AttachmentMapper {
    Attachment toModel(AttachmentRespopnseDto attachmentRespopnseDto);

    AttachmentRespopnseDto toDto(Attachment attachment);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Attachment partialUpdate(AttachmentRespopnseDto attachmentRespopnseDto,
                             @MappingTarget Attachment attachment);
}
