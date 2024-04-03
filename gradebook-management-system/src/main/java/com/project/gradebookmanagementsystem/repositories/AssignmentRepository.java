package com.project.gradebookmanagementsystem.repositories;

import com.project.gradebookmanagementsystem.models.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
}
