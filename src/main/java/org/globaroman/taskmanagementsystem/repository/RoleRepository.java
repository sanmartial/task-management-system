package org.globaroman.taskmanagementsystem.repository;

import org.globaroman.taskmanagementsystem.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
