package org.globaroman.taskmanagementsystem.repository;

import java.util.List;
import org.globaroman.taskmanagementsystem.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findAllByUserId(Long id);
}
