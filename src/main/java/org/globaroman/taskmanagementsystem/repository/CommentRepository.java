package org.globaroman.taskmanagementsystem.repository;

import org.globaroman.taskmanagementsystem.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByTaskId(Long taskId);
}
