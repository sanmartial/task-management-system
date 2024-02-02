package org.globaroman.taskmanagementsystem.repository;

import java.util.List;
import org.globaroman.taskmanagementsystem.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByTaskId(Long taskId);
}
