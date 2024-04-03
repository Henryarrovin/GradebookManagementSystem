package com.project.gradebookmanagementsystem.repositories;

import com.project.gradebookmanagementsystem.models.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
}
