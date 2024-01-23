package org.globaroman.taskmanagementsystem.service.impl;

import jakarta.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.globaroman.taskmanagementsystem.dto.user.UpdateRoleDto;
import org.globaroman.taskmanagementsystem.dto.user.UserRegistrationRequestDto;
import org.globaroman.taskmanagementsystem.dto.user.UserResponseDto;
import org.globaroman.taskmanagementsystem.exception.EntityNotFoundCustomException;
import org.globaroman.taskmanagementsystem.exception.RegistrationException;
import org.globaroman.taskmanagementsystem.mapper.UserMapper;
import org.globaroman.taskmanagementsystem.model.Role;
import org.globaroman.taskmanagementsystem.model.RoleName;
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
            (long) RoleName.USER.ordinal() + 1;

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
        User saved = userRepository.save(user);
        UserResponseDto dto = userMapper.toDto(saved);
        return dto;
    }

    @Override
    @Transactional
    public UserResponseDto update(Long id, UpdateRoleDto updateRoleName) {
        RoleName roleName = RoleName.valueOf(updateRoleName.getRole().name());
        User user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundCustomException("No found user by id: " + id)
        );
        Role existRole = getRoleFromDB((long)roleName.ordinal() + 1);

        Set<Role> roles = new HashSet<>();
        roles.add(existRole);
        user.setRoles(roles);
        User saved = userRepository.save(user);
        UserResponseDto dto = userMapper.toDto(saved);
        return dto;
    }

    @Override
    public List<UserResponseDto> getAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
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
