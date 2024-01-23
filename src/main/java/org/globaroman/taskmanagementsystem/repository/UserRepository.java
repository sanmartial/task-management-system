package org.globaroman.taskmanagementsystem.repository;

import java.util.Optional;
import org.globaroman.taskmanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
