package com.project.gradebookmanagementsystem.controllers;

import com.project.gradebookmanagementsystem.dtos.StudentDto;
import com.project.gradebookmanagementsystem.mappers.Mapper;
import com.project.gradebookmanagementsystem.models.Student;
import com.project.gradebookmanagementsystem.services.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private StudentService studentService;
    private Mapper<Student, StudentDto> studentMapper;

    public StudentController(StudentService studentService, Mapper<Student, StudentDto> studentMapper) {
        this.studentService = studentService;
        this.studentMapper = studentMapper;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello!";
    }

    @PostMapping("/login")
    public ResponseEntity<StudentDto> loginStudent(@RequestBody StudentDto studentDto) {
        Student student = studentService.findByStudentIdAndName(studentDto.getId(), studentDto.getFirstName());
        if (student == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(studentMapper.mapTo(student), HttpStatus.OK);
    }

    @PostMapping("/create-student")
    public ResponseEntity<StudentDto> createStudent(@RequestBody StudentDto studentDto) {

        if (studentDto.getFirstName() == null ||
                studentDto.getLastName() == null ||
                studentDto.getEmail() == null ||
                studentDto.getDateOfBirth() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Student student = studentMapper.mapFrom(studentDto);
        Student createdStudent = studentService.createStudent(student);
        return new ResponseEntity<>(studentMapper.mapTo(createdStudent), HttpStatus.CREATED);
    }

    @PutMapping("/update-student/{id}")
    public ResponseEntity<StudentDto> updateStudent(@PathVariable Long id, @RequestBody StudentDto studentDto) {

        if (!studentService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Student student = studentMapper.mapFrom(studentDto);
        Student updatedStudent = studentService.updateStudent(id, student);
        return new ResponseEntity<>(studentMapper.mapTo(updatedStudent), HttpStatus.OK);
    }

    @GetMapping("/get-all-students")
    public ResponseEntity<Iterable<StudentDto>> getAllStudents() {
        Iterable<Student> students = studentService.getAllStudents();
        List<StudentDto> studentDto = new ArrayList<>();
        for (Student student : students) {
            studentDto.add(studentMapper.mapTo(student));
        }
        return new ResponseEntity<>(studentDto, HttpStatus.OK);
    }

    @GetMapping("/get-student/{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable Long id) {

        Student student = studentService.getStudentById(id);


        if (!studentService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(studentMapper.mapTo(student), HttpStatus.OK);
    }

    @DeleteMapping("/delete-student/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
