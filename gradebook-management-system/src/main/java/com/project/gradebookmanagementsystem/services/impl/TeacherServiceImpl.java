package com.project.gradebookmanagementsystem.services.impl;

import com.project.gradebookmanagementsystem.models.Teacher;
import com.project.gradebookmanagementsystem.repositories.TeacherRepository;
import com.project.gradebookmanagementsystem.services.TeacherService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherServiceImpl implements TeacherService {

    private TeacherRepository teacherRepository;
    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public Teacher findByTeacherIdAndName(Long id, String name) {
        return teacherRepository.findByIdAndFirstName(id, name);
    }

    @Override
    public Teacher createTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @Override
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    @Override
    public Teacher getTeacherById(Long id) {
        return teacherRepository.findById(id).orElse(null);
    }

    @Override
    public Teacher updateTeacher(Long id, Teacher teacher) {
        teacher.setId(id);
        return teacherRepository.findById(id)
                .map(existingTeacher -> {
                    Optional.ofNullable(teacher.getFirstName()).ifPresent(existingTeacher::setFirstName);
                    Optional.ofNullable(teacher.getLastName()).ifPresent(existingTeacher::setLastName);
                    Optional.ofNullable(teacher.getEmail()).ifPresent(existingTeacher::setEmail);
                    return teacherRepository.save(existingTeacher);
                }).orElseThrow(() -> new RuntimeException("Teacher not found"));
    }

    @Override
    public void deleteTeacher(Long id) {
        teacherRepository.deleteById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return teacherRepository.existsById(id);
    }
}
