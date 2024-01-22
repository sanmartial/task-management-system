package org.globaroman.taskmanagementsystem.service.impl;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.globaroman.taskmanagementsystem.dto.user.UserRegistrationRequestDto;
import org.globaroman.taskmanagementsystem.dto.user.UserResponseDto;
import org.globaroman.taskmanagementsystem.exception.RegistrationException;
import org.globaroman.taskmanagementsystem.mapper.UserMapper;
import org.globaroman.taskmanagementsystem.model.Role;
import org.globaroman.taskmanagementsystem.model.User;
import org.globaroman.taskmanagementsystem.repository.RoleRepository;
import org.globaroman.taskmanagementsystem.repository.UserRepository;
import org.globaroman.taskmanagementsystem.service.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Long USER_ROLE_ID =
            (long) Role.RoleName.USER.ordinal() + 1;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new RegistrationException(
                    "Can't register user. The same user with email "
                            + requestDto.getEmail()
                            + " already exist");
        }

        User user = getUserWithRoleAndPasswordEncode(requestDto);
        return userMapper.toDto(userRepository.save(user));
    }

    private User getUserWithRoleAndPasswordEncode(UserRegistrationRequestDto requestDto) {
        User user = userMapper.toModel(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setRoles(Set.of(getRoleFromDB(USER_ROLE_ID)));
        return user;
    }

    private Role getRoleFromDB(Long id) {
        return roleRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("Can't find role with id: " + id));
    }
}
