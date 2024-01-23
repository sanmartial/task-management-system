package org.globaroman.taskmanagementsystem.dto.user;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.globaroman.taskmanagementsystem.model.Role;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Set<Role> roles;
}
