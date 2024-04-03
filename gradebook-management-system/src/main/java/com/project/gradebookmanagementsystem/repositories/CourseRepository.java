package com.project.gradebookmanagementsystem.repositories;

import com.project.gradebookmanagementsystem.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
