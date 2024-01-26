package org.globaroman.taskmanagementsystem.repository;

import java.util.List;
import org.globaroman.taskmanagementsystem.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByProjectId(Long id);
}
