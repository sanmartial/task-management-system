package org.globaroman.taskmanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.globaroman.taskmanagementsystem.dto.user.UserLoginRequestDto;
import org.globaroman.taskmanagementsystem.dto.user.UserLoginResponseDto;
import org.globaroman.taskmanagementsystem.dto.user.UserRegistrationRequestDto;
import org.globaroman.taskmanagementsystem.dto.user.UserResponseDto;
import org.globaroman.taskmanagementsystem.model.Role;
import org.globaroman.taskmanagementsystem.model.RoleName;
import org.globaroman.taskmanagementsystem.service.AuthenticationService;
import org.globaroman.taskmanagementsystem.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthenticationService authenticationService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    @DisplayName("User registration must be successful")
    void register_ValidData_ShouldSuccessfulRegistration() throws Exception {
        UserRegistrationRequestDto requestDto = new UserRegistrationRequestDto(
                "test@example.com",
                "password",
                "password",
                "John",
                "Doe"
        );
        Set<Role> roles = new HashSet<>();
        Role role = new Role(2L, RoleName.ADMIN);
        roles.add(role);
        Mockito.when(userService.register(Mockito.any())).thenReturn(new UserResponseDto(
                1L,
                "test@example.com",
                "John",
                "Doe",
                roles
                ));
        mockMvc.perform(post("/api/auth/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("User login must be successful")
    void login_ValidData_SuccessfulLogin() throws Exception {
        UserLoginRequestDto userLoginRequestDto =
                new UserLoginRequestDto("test@example.com", "password");
        Mockito.when(authenticationService.authenticate(Mockito.any()))
                .thenReturn(new UserLoginResponseDto("testIsToken"));

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userLoginRequestDto)))
                .andExpect(status().isAccepted());
    }
}
