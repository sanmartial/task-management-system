package org.globaroman.taskmanagementsystem.service;

import java.util.List;
import org.globaroman.taskmanagementsystem.dto.user.UpdateRoleDto;
import org.globaroman.taskmanagementsystem.dto.user.UserRegistrationRequestDto;
import org.globaroman.taskmanagementsystem.dto.user.UserResponseDto;
import org.globaroman.taskmanagementsystem.exception.RegistrationException;

public interface UserService {

    UserResponseDto register(UserRegistrationRequestDto requestDto) throws RegistrationException;

    UserResponseDto update(Long id, UpdateRoleDto role);

    List<UserResponseDto> getAll();

    void deleteById(Long id);
}
