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
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    private CourseRepository courseRepository;
    private UserRepository userRepository;

    public CourseServiceImpl(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

//    @Override
//    public Course createCourse(Course course) {
//        User teacher = userRepository.findById(course.getTeacher().getId()).orElse(null);
//        if (teacher == null) {
//            throw new RuntimeException("Teacher not found");
//        }
//
//        Course newCourse = new Course();
//        newCourse.setName(course.getName());
//        newCourse.setTeacher(teacher);
//
//        return courseRepository.save(newCourse);
//    }

    @Override
    public Course createCourse(Course course) {
        User teacher = userRepository.findById(course.getTeacher().getId()).orElse(null);
        if (teacher == null) {
            throw new RuntimeException("Teacher not found");
        }

        // Retrieve enrolled students who have the role of a student
//        List<User> enrolledStudents = course.getEnrolledStudents().stream()
//                .filter(student -> student.getRoles().stream().anyMatch(role -> role.getName().equals("STUDENT")))
//                .collect(Collectors.toList());

        List<User> enrolledStudents = course.getEnrolledStudents().stream()
                .map(studentDto -> {
                    User student = userRepository.findById(studentDto.getId())
                            .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentDto.getId()));

                    // Check if the student has the role of a student
                    if (student.getRoles().stream().noneMatch(role -> role.getName().equals("STUDENT"))) {
                        throw new RuntimeException("User with ID " + student.getId() + " is not a student");
                    }
                    return student;
                })
                .collect(Collectors.toList());

        Course newCourse = new Course();
        newCourse.setName(course.getName());
        newCourse.setTeacher(teacher);
        newCourse.setEnrolledStudents(enrolledStudents);

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

//    @Override
//    public Course updateCourse(Long id, Course course) {
//        course.setId(id);
//        User teacher = userRepository.findById(course.getTeacher().getId()).orElse(null);
//        if (teacher == null) {
//            throw new RuntimeException("Teacher not found");
//        }
//        return courseRepository.findById(id)
//                .map(existingCourse -> {
//                    Optional.ofNullable(course.getName()).ifPresent(existingCourse::setName);
//                    existingCourse.setTeacher(teacher);
//
//                    return courseRepository.save(existingCourse);
//                }).orElseThrow(() -> new RuntimeException("Course not found"));
//    }

    @Override
    public Course updateCourse(Long id, Course course) {
        course.setId(id);
        Course existingCourse = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        // Update the existing course with the new information if provided
        if (course.getName() != null) {
            existingCourse.setName(course.getName());
        }

        if (course.getTeacher() != null) {
            User teacher = userRepository.findById(course.getTeacher().getId())
                    .orElseThrow(() -> new RuntimeException("Teacher not found"));
            existingCourse.setTeacher(teacher);
        }

        // Retrieve enrolled students who have the role of a student
        if (course.getEnrolledStudents() != null && !course.getEnrolledStudents().isEmpty()) {
            List<User> enrolledStudents = course.getEnrolledStudents().stream()
                    .map(studentDto -> userRepository.findById(studentDto.getId())
                            .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentDto.getId())))
                    .filter(student -> student.getRoles().stream().anyMatch(role -> role.getName().equals("STUDENT")))
                    .collect(Collectors.toList());
            existingCourse.setEnrolledStudents(enrolledStudents);
        }

        return courseRepository.save(existingCourse);
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
