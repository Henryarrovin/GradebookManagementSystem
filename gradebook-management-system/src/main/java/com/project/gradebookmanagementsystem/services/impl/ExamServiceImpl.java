package com.project.gradebookmanagementsystem.services.impl;


import com.project.gradebookmanagementsystem.models.Exam;
import com.project.gradebookmanagementsystem.repositories.ExamRepository;
import com.project.gradebookmanagementsystem.services.ExamService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExamServiceImpl implements ExamService {

    private ExamRepository examRepository;

    public ExamServiceImpl(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    @Override
    public Exam createExam(Exam exam) {
        return examRepository.save(exam);
    }

    @Override
    public List<Exam> getAllExams() {
        return examRepository.findAll();
    }

    @Override
    public Exam getExamById(Long id) {
        return examRepository.findById(id).orElse(null);
    }

    @Override
    public Exam updateExam(Long id, Exam exam) {
        exam.setId(id);
        return examRepository.findById(id)
                .map(existingTeacher -> {
                    Optional.ofNullable(exam.getTitle()).ifPresent(existingTeacher::setTitle);
                    Optional.ofNullable(exam.getDescription()).ifPresent(existingTeacher::setDescription);
                    Optional.ofNullable(exam.getExamDate()).ifPresent(existingTeacher::setExamDate);
                    return examRepository.save(existingTeacher);
                }).orElseThrow(() -> new RuntimeException("Teacher not found"));
    }

    @Override
    public void deleteExam(Long id) {
        examRepository.deleteById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return examRepository.existsById(id);
    }
}
