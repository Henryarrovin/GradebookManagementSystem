package com.project.gradebookmanagementsystem.controllers;

import com.project.gradebookmanagementsystem.dtos.CourseDto;
import com.project.gradebookmanagementsystem.mappers.Mapper;
import com.project.gradebookmanagementsystem.models.Course;
import com.project.gradebookmanagementsystem.models.Student;
import com.project.gradebookmanagementsystem.services.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private CourseService courseService;
    private Mapper<Course, CourseDto> courseMapper;

    public CourseController(CourseService courseService, Mapper<Course, CourseDto> courseMapper) {
        this.courseService = courseService;
        this.courseMapper = courseMapper;
    }

    @PostMapping("/create-course")
    public ResponseEntity<CourseDto> createCourse(@RequestBody CourseDto courseDto) {

        if (courseDto.getName() == null ||
                courseDto.getTeacher() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Course course = courseMapper.mapFrom(courseDto);
        Course createdCourse = courseService.createCourse(course);
        return new ResponseEntity<>(courseMapper.mapTo(createdCourse), HttpStatus.CREATED);
    }

    @PutMapping("/update-course/{id}")
    public ResponseEntity<CourseDto> updateCourse(@PathVariable Long id, @RequestBody CourseDto courseDto) {
        if (!courseService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Course course = courseMapper.mapFrom(courseDto);
        Course updatedCourse = courseService.updateCourse(id, course);
        return new ResponseEntity<>(courseMapper.mapTo(updatedCourse), HttpStatus.OK);
    }

    @GetMapping("/get-all-courses")
    public ResponseEntity<Iterable<CourseDto>> getAllCourses() {
        Iterable<Course> courses = courseService.getAllCourses();
        List<CourseDto> courseDto = new ArrayList<>();
        for (Course course : courses) {
            courseDto.add(courseMapper.mapTo(course));
        }
        return new ResponseEntity<>(courseDto, HttpStatus.OK);
    }

    @GetMapping("/get-course/{id}")
    public ResponseEntity<CourseDto> getCourseById(@PathVariable Long id) {

        Course course = courseService.getCourseById(id);

        if (!courseService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(courseMapper.mapTo(course), HttpStatus.OK);
    }

    @DeleteMapping("delete-course/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
