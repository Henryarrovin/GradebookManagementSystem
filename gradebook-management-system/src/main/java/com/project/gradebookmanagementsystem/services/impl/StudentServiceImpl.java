package com.project.gradebookmanagementsystem.services.impl;

import com.project.gradebookmanagementsystem.models.Student;
import com.project.gradebookmanagementsystem.models.Teacher;
import com.project.gradebookmanagementsystem.repositories.StudentRepository;
import com.project.gradebookmanagementsystem.services.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    @Override
    public Student findByStudentIdAndName(Long id, String name) {
        return studentRepository.findByIdAndFirstName(id, name);
    }

    @Override
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(Long id, Student student) {
        student.setId(id);
        return studentRepository.findById(id)
                .map(existingStudent -> {
                    Optional.ofNullable(student.getFirstName()).ifPresent(existingStudent::setFirstName);
                    Optional.ofNullable(student.getLastName()).ifPresent(existingStudent::setLastName);
                    Optional.ofNullable(student.getEmail()).ifPresent(existingStudent::setEmail);
                    Optional.ofNullable(student.getDateOfBirth()).ifPresent(existingStudent::setDateOfBirth);
                    return studentRepository.save(existingStudent);
                }).orElseThrow(() -> new RuntimeException("Student not found"));
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return studentRepository.existsById(id);
    }
}
