package com.project.gradebookmanagementsystem.services;

import com.project.gradebookmanagementsystem.models.Course;

import java.util.List;

public interface CourseService {

    List<Course> getAllCourses();

    Course getCourseById(Long id);

    Course createCourse(Course course);

    Course updateCourse(Long id, Course course);

    void deleteCourse(Long id);

}
