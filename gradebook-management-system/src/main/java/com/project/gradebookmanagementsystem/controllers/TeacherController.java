package com.project.gradebookmanagementsystem.controllers;

import com.project.gradebookmanagementsystem.dtos.StudentDto;
import com.project.gradebookmanagementsystem.dtos.TeacherDto;
import com.project.gradebookmanagementsystem.mappers.Mapper;
import com.project.gradebookmanagementsystem.models.Student;
import com.project.gradebookmanagementsystem.models.Teacher;
import com.project.gradebookmanagementsystem.services.TeacherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private TeacherService teacherService;
    private Mapper<Teacher, TeacherDto> teacherMapper;

    public TeacherController(TeacherService teacherService, Mapper<Teacher, TeacherDto> teacherMapper) {
        this.teacherService = teacherService;
        this.teacherMapper = teacherMapper;
    }

    @PostMapping("/login")
    public ResponseEntity<TeacherDto> loginTeacher(@RequestBody TeacherDto teacherDto) {
        Teacher teacher = teacherService.findByTeacherIdAndName(teacherDto.getId(), teacherDto.getFirstName());
        if (teacher == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(teacherMapper.mapTo(teacher), HttpStatus.OK);
    }

    @PostMapping("/create-teacher")
    public ResponseEntity<TeacherDto> createTeacher(@RequestBody TeacherDto teacherDto) {

        if (teacherDto.getFirstName() == null ||
                teacherDto.getLastName() == null ||
                teacherDto.getEmail() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Teacher teacher = teacherMapper.mapFrom(teacherDto);
        Teacher createdTeacher = teacherService.createTeacher(teacher);
        return new ResponseEntity<>(teacherMapper.mapTo(createdTeacher), HttpStatus.CREATED);
    }

    @GetMapping("/get-all-teachers")
    public ResponseEntity<Iterable<TeacherDto>> getAllTeachers() {
        Iterable<Teacher> teachers = teacherService.getAllTeachers();
        List<TeacherDto> teacherDto = new ArrayList<>();
        for (Teacher teacher : teachers) {
            teacherDto.add(teacherMapper.mapTo(teacher));
        }
        return new ResponseEntity<>(teacherDto, HttpStatus.OK);
    }

    @GetMapping("/get-teacher/{id}")
    public ResponseEntity<TeacherDto> getTeacherById(@PathVariable Long id) {

        Teacher teacher = teacherService.getTeacherById(id);


        if (!teacherService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(teacherMapper.mapTo(teacher), HttpStatus.OK);
    }

    @PutMapping("/update-teacher/{id}")
    public ResponseEntity<TeacherDto> updateTeacher(@PathVariable Long id, @RequestBody TeacherDto teacherDto) {

        if (!teacherService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Teacher teacher = teacherMapper.mapFrom(teacherDto);
        Teacher updatedTeacher = teacherService.updateTeacher(id, teacher);
        return new ResponseEntity<>(teacherMapper.mapTo(updatedTeacher), HttpStatus.OK);
    }

    @DeleteMapping("/delete-teacher/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
