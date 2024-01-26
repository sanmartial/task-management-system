package org.globaroman.taskmanagementsystem.repository;

import org.globaroman.taskmanagementsystem.dto.task.TaskResponseDto;
import org.globaroman.taskmanagementsystem.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByProjectId(Long id);
}
