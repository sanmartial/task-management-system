package org.globaroman.taskmanagementsystem.mapper;

import org.globaroman.taskmanagementsystem.config.MapperConfig;
import org.globaroman.taskmanagementsystem.dto.comment.CommentResponseDto;
import org.globaroman.taskmanagementsystem.dto.comment.CreateCommentRequireDto;
import org.globaroman.taskmanagementsystem.model.Comment;
import org.globaroman.taskmanagementsystem.model.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = MapperConfig.class, uses = User.class)
public interface CommentMapper {
    Comment toModel(CreateCommentRequireDto createCommentRequireDto);

    @Mapping(target = "userName", ignore = true)
    CommentResponseDto toDto(Comment comment);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Comment partialUpdate(CreateCommentRequireDto createCommentRequireDto,
                          @MappingTarget Comment comment);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Comment partialUpdate(CommentResponseDto commentResponseDto, @MappingTarget Comment comment);

    @AfterMapping
    default void setUserName(@MappingTarget CommentResponseDto commentResponseDto,
                             Comment comment) {
        commentResponseDto.setUserName(comment.getUser().getFirstName());
    }
}
