package com.project.gradebookmanagementsystem.services;

import com.project.gradebookmanagementsystem.models.Student;

import java.util.List;

public interface StudentService {

    Student createStudent(Student student);
    Student updateStudent(Long id, Student student);

    List<Student> getAllStudents();

    Student getStudentById(Long id);

    void deleteStudent(Long id);

    boolean isExists(Long id);

}
