package org.globaroman.taskmanagementsystem.mapper;

import org.globaroman.taskmanagementsystem.config.MapperConfig;
import org.globaroman.taskmanagementsystem.dto.user.UserRegistrationRequestDto;
import org.globaroman.taskmanagementsystem.dto.user.UserResponseDto;
import org.globaroman.taskmanagementsystem.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto toDto(User user);

    User toModel(UserRegistrationRequestDto requestDto);

}
