package com.project.gradebookmanagementsystem.services;

import com.project.gradebookmanagementsystem.models.Teacher;

import java.util.List;

public interface TeacherService {

    Teacher findByTeacherIdAndName(Long id, String name);
    Teacher createTeacher(Teacher teacher);

    List<Teacher> getAllTeachers();

    Teacher getTeacherById(Long id);

    Teacher updateTeacher(Long id, Teacher teacher);

    void deleteTeacher(Long id);

    boolean isExists(Long id);
}
