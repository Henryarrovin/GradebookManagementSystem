package com.project.gradebookmanagementsystem.services;

import com.project.gradebookmanagementsystem.models.Grade;

import java.util.List;

public interface GradeService {

    List<Grade> getAllGrades();

    Grade getGradeById(Long id);

    Grade createGrade(Grade grade);

    Grade updateGrade(Long id, Grade grade);

    void deleteGrade(Long id);

    boolean isExists(Long id);
}
