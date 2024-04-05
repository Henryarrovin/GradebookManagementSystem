package com.project.gradebookmanagementsystem.services.impl;

import com.project.gradebookmanagementsystem.models.Grade;
import com.project.gradebookmanagementsystem.repositories.GradeRepository;
import com.project.gradebookmanagementsystem.services.GradeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GradeServiceImpl implements GradeService {

    private GradeRepository gradeRepository;

    public GradeServiceImpl(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    @Override
    public Grade createGrade(Grade grade) {
        return gradeRepository.save(grade);
    }

    @Override
    public List<Grade> getAllGrades() {
        return gradeRepository.findAll();
    }

    @Override
    public Grade getGradeById(Long id) {
        return gradeRepository.findById(id).orElse(null);
    }

    @Override
    public Grade updateGrade(Long id, Grade grade) {
        grade.setId(id);
        return gradeRepository.findById(id)
                .map(existingGrade -> {
                    Optional.ofNullable(grade.getStudent()).ifPresent(existingGrade::setStudent);
                    Optional.ofNullable(grade.getAssignment()).ifPresent(existingGrade::setAssignment);
                    Optional.ofNullable(grade.getExam()).ifPresent(existingGrade::setExam);
                    Optional.ofNullable(grade.getGrade()).ifPresent(existingGrade::setGrade);
                    return gradeRepository.save(existingGrade);
                }).orElseThrow(() -> new RuntimeException("Grade not found"));
    }

    @Override
    public void deleteGrade(Long id) {
        gradeRepository.deleteById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return gradeRepository.existsById(id);
    }
}
