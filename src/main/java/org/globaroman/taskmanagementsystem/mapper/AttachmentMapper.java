package org.globaroman.taskmanagementsystem.mapper;

import org.globaroman.taskmanagementsystem.config.MapperConfig;
import org.globaroman.taskmanagementsystem.dto.attachment.AttachmentResponseDto;
import org.globaroman.taskmanagementsystem.dto.attachment.CreateAttachmentRequireDto;
import org.globaroman.taskmanagementsystem.model.Attachment;
import org.globaroman.taskmanagementsystem.model.Task;
import org.globaroman.taskmanagementsystem.model.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = MapperConfig.class)
public interface AttachmentMapper {
    Attachment toModel(AttachmentResponseDto attachmentRespopnseDto);

    AttachmentResponseDto toDto(Attachment attachment);

    @Mapping(target = "uploadDate", expression = "java(LocalDateTime.now())")
    @Mapping(target = "id", ignore = true)
    Attachment toEntity(CreateAttachmentRequireDto requireDto, Task task, User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Attachment partialUpdate(AttachmentResponseDto attachmentRespopnseDto,
                             @MappingTarget Attachment attachment);
}
