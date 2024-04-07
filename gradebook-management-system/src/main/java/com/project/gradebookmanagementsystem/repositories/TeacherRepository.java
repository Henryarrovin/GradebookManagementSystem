package com.project.gradebookmanagementsystem.repositories;

import com.project.gradebookmanagementsystem.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Teacher findByIdAndFirstName(Long id, String name);

    Teacher findByUsername(String username);
}
