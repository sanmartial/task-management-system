package org.globaroman.taskmanagementsystem.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.globaroman.taskmanagementsystem.model.RoleName;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRoleDto {
    private RoleName role;
}
