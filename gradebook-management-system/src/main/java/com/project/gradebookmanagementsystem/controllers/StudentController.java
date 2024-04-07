package com.project.gradebookmanagementsystem.controllers;

import com.project.gradebookmanagementsystem.dtos.StudentDto;
import com.project.gradebookmanagementsystem.mappers.Mapper;
import com.project.gradebookmanagementsystem.models.Student;
import com.project.gradebookmanagementsystem.security.JwtTokenProvider;
import com.project.gradebookmanagementsystem.services.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private StudentService studentService;
    private Mapper<Student, StudentDto> studentMapper;
    private JwtTokenProvider jwtTokenProvider;

    public StudentController(StudentService studentService, Mapper<Student, StudentDto> studentMapper, JwtTokenProvider jwtTokenProvider) {
        this.studentService = studentService;
        this.studentMapper = studentMapper;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello!";
    }

//    @PostMapping("/login")
//    public ResponseEntity<StudentDto> loginStudent(@RequestBody StudentDto studentDto) {
//        Student student = studentService.findByStudentIdAndName(studentDto.getId(), studentDto.getFirstName());
//        if (student == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(studentMapper.mapTo(student), HttpStatus.OK);
//    }

//    @PostMapping("/login")
//    public ResponseEntity<?> loginStudent(@RequestBody StudentDto studentDto) {
//        // Attempt to find the student by ID and name
//        Student student = studentService.findByStudentIdAndName(studentDto.getId(), studentDto.getFirstName());
//        if (student == null) {
//            // If the student is not found, return a 404 Not Found status
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        // Assuming you have a method to validate the student's ID and first name
//        // This part seems redundant since you've already found the student by ID and name
//        // You might want to remove or adjust this part based on your actual validation logic
//
//        // Generate JWT token
//        String token = jwtTokenProvider.generateToken(student);
//        // Return the token in the response
//        return ResponseEntity.ok(token);
//    }


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
