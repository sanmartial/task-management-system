package org.globaroman.taskmanagementsystem.mapper;

import java.util.Optional;
import org.globaroman.taskmanagementsystem.config.MapperConfig;
import org.globaroman.taskmanagementsystem.dto.user.UserRegistrationRequestDto;
import org.globaroman.taskmanagementsystem.dto.user.UserResponseDto;
import org.globaroman.taskmanagementsystem.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto toDto(User user);

    User toModel(UserRegistrationRequestDto requestDto);

}
