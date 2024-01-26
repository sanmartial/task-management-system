package org.globaroman.taskmanagementsystem.repository;

import java.util.List;
import org.globaroman.taskmanagementsystem.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    List<Attachment> findByTaskId(Long taskId);
}
