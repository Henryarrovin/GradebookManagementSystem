package com.project.gradebookmanagementsystem.services.impl;

import com.project.gradebookmanagementsystem.models.Course;
import com.project.gradebookmanagementsystem.repositories.CourseRepository;
import com.project.gradebookmanagementsystem.services.CourseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    private CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Course getCourseById(Long id) {
        return courseRepository.findById(id).orElse(null);
    }

    @Override
    public Course updateCourse(Long id, Course course) {
        course.setId(id);
        return courseRepository.findById(id)
                .map(existingCourse -> {
                    Optional.ofNullable(course.getName()).ifPresent(existingCourse::setName);
                    Optional.ofNullable(course.getTeacher()).ifPresent(existingCourse::setTeacher);
                    return courseRepository.save(existingCourse);
                }).orElseThrow(() -> new RuntimeException("Course not found"));
    }

    @Override
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return courseRepository.existsById(id);
    }
}
