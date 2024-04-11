package com.project.gradebookmanagementsystem.services.impl;

import com.project.gradebookmanagementsystem.models.Course;
import com.project.gradebookmanagementsystem.models.User;
import com.project.gradebookmanagementsystem.repositories.CourseRepository;
import com.project.gradebookmanagementsystem.repositories.UserRepository;
import com.project.gradebookmanagementsystem.services.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    private CourseRepository courseRepository;
    private UserRepository userRepository;

    public CourseServiceImpl(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Course createCourse(Course course) {
        User teacher = userRepository.findById(course.getTeacher().getId()).orElse(null);
        if (teacher == null) {
            throw new RuntimeException("Teacher not found");
        }

        Course newCourse = new Course();
        newCourse.setName(course.getName());
        newCourse.setTeacher(teacher);

        return courseRepository.save(newCourse);
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
        User teacher = userRepository.findById(course.getTeacher().getId()).orElse(null);
        if (teacher == null) {
            throw new RuntimeException("Teacher not found");
        }
        return courseRepository.findById(id)
                .map(existingCourse -> {
                    Optional.ofNullable(course.getName()).ifPresent(existingCourse::setName);
                    existingCourse.setTeacher(teacher);

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
