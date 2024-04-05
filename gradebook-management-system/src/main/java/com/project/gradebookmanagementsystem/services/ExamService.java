package com.project.gradebookmanagementsystem.services;

import com.project.gradebookmanagementsystem.models.Exam;

import java.util.List;

public interface ExamService {

    List<Exam> getAllExams();

    Exam getExamById(Long id);

    Exam createExam(Exam exam);

    Exam updateExam(Long id, Exam exam);

    void deleteExam(Long id);

    boolean isExists(Long id);
}
