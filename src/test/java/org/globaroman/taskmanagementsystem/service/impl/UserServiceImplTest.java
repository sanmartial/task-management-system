package org.globaroman.taskmanagementsystem.service.impl;

import jakarta.transaction.Transactional;
import java.util.Optional;
import org.globaroman.taskmanagementsystem.dto.user.UpdateRoleDto;
import org.globaroman.taskmanagementsystem.dto.user.UserRegistrationRequestDto;
import org.globaroman.taskmanagementsystem.dto.user.UserResponseDto;
import org.globaroman.taskmanagementsystem.exception.RegistrationException;
import org.globaroman.taskmanagementsystem.mapper.UserMapper;
import org.globaroman.taskmanagementsystem.model.Role;
import org.globaroman.taskmanagementsystem.model.RoleName;
import org.globaroman.taskmanagementsystem.model.User;
import org.globaroman.taskmanagementsystem.repository.RoleRepository;
import org.globaroman.taskmanagementsystem.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

//@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Register user -> Successful return UserResponseDto")
    void register_RegisterValidUser_ReturnUserResponseDto() throws RegistrationException {

        UserRegistrationRequestDto requestDto = new UserRegistrationRequestDto();
        requestDto.setEmail("test@example.com");
        requestDto.setPassword("password");
        requestDto.setRepeatPassword("password");
        requestDto.setFirstName("David");
        requestDto.setLastName("Beckham");

        Mockito.when(userMapper.toModel(requestDto)).thenReturn(new User());

        Mockito.when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setId(1L);
            savedUser.setEmail("test@example.com"); // Установим email вручную
            return savedUser;
        });

        Mockito.when(userMapper.toDto(Mockito.any(User.class))).thenReturn(new UserResponseDto());

        Role mockRole = new Role();
        mockRole.setId(1L);
        Mockito.when(roleRepository.findById(1L)).thenReturn(Optional.of(mockRole));

        UserResponseDto result = userService.register(requestDto);

        Assertions.assertNotNull(result);

        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(User.class));
        Mockito.verify(userMapper, Mockito.times(1)).toDto(Mockito.any(User.class));
    }

    @Test
    @DisplayName("Update role user -> Successful return UserResponseDto with uprate role")
    @Transactional
    void update_ValidIdAndRole_ReturnsUserResponseDto() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        RoleName roleName = RoleName.USER;
        Role mockRole = new Role();
        mockRole.setId(1L);

        UpdateRoleDto updateRoleDto = new UpdateRoleDto(roleName);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Mockito.when(userRepository.save(Mockito.any(User.class)))
                .thenAnswer(invocation -> {
                    User savedUser = invocation.getArgument(0);
                    savedUser.setId(1L);
                    return savedUser;
                });

        Mockito.when(roleRepository.findById(1L)).thenReturn(Optional.of(mockRole));

        Mockito.when(userMapper.toDto(Mockito.any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            UserResponseDto userResponseDto = new UserResponseDto();
            userResponseDto.setId(savedUser.getId());
            return userResponseDto;
        });

        UserResponseDto result = userService.update(1L, updateRoleDto);

        Assertions.assertEquals(1L, result.getId());
    }

    @Test
    void getAll() {
    }

    @Test
    void deleteById() {
    }
}
