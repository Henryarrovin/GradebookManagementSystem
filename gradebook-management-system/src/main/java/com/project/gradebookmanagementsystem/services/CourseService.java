package com.project.gradebookmanagementsystem.services;

import com.project.gradebookmanagementsystem.models.Course;

import java.util.List;

public interface CourseService {

    Course createCourse(Course course);

    List<Course> getAllCourses();

    Course getCourseById(Long id);

    Course updateCourse(Long id, Course course);

    void deleteCourse(Long id);

    boolean isExists(Long id);
}
