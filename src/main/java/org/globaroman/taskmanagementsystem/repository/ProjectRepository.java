package org.globaroman.taskmanagementsystem.repository;

import org.globaroman.taskmanagementsystem.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findAllByUserId(Long id);
}
