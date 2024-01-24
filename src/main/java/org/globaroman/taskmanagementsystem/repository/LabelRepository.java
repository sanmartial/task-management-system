package org.globaroman.taskmanagementsystem.repository;

import org.globaroman.taskmanagementsystem.model.Label;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabelRepository extends JpaRepository<Label, Long> {
}
