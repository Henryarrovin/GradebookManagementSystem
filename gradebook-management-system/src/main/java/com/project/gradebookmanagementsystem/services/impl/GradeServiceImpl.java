package com.project.gradebookmanagementsystem.services.impl;

import com.project.gradebookmanagementsystem.models.Assignment;
import com.project.gradebookmanagementsystem.models.Exam;
import com.project.gradebookmanagementsystem.models.Grade;
import com.project.gradebookmanagementsystem.models.User;
import com.project.gradebookmanagementsystem.repositories.AssignmentRepository;
import com.project.gradebookmanagementsystem.repositories.ExamRepository;
import com.project.gradebookmanagementsystem.repositories.GradeRepository;
import com.project.gradebookmanagementsystem.repositories.UserRepository;
import com.project.gradebookmanagementsystem.services.GradeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GradeServiceImpl implements GradeService {

    private GradeRepository gradeRepository;
    private UserRepository userRepository;
    private AssignmentRepository assignmentRepository;
    private ExamRepository examRepository;

    public GradeServiceImpl(GradeRepository gradeRepository, UserRepository userRepository, AssignmentRepository assignmentRepository, ExamRepository examRepository) {
        this.gradeRepository = gradeRepository;
        this.userRepository = userRepository;
        this.assignmentRepository = assignmentRepository;
        this.examRepository = examRepository;
    }

    @Override
    public Grade createGrade(Grade grade) {
        if (grade.getAssignment() == null && grade.getExam() == null) {
            throw new RuntimeException("Assignment or Exam not found");
        }

        if (grade.getAssignment() != null && grade.getExam() != null) {
            throw new RuntimeException("You can't assign a grade to both Assignment and Exam");
        }

        if (grade.getAssignment() != null) {
            Assignment assignment = assignmentRepository.findById(grade.getAssignment().getId()).orElse(null);
            grade.setAssignment(assignment);
            grade.setExam(null);
        } else {
            if (grade.getExam() == null) {
                throw new RuntimeException("Exam not found");
            }
            Exam exam = examRepository.findById(grade.getExam().getId()).orElse(null);
            if (exam == null) {
                throw new RuntimeException("Exam not found");
            }
            grade.setExam(exam);
            grade.setAssignment(null);
        }

        User student = userRepository.findById(grade.getStudent().getId()).orElse(null);
        if (student == null) {
            throw new RuntimeException("Student not found");
        }
        grade.setStudent(student);

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
        User student = userRepository.findById(grade.getStudent().getId()).orElseThrow(() -> new RuntimeException("Student not found"));

        if (grade.getAssignment() == null && grade.getExam() == null) {
            throw new RuntimeException("Assignment or Exam not found");
        }

        if (grade.getAssignment() != null && grade.getExam() != null) {
            throw new RuntimeException("You can't assign grade to both Assignment and Exam");
        }

        return gradeRepository.findById(id)
                .map(existingGrade -> {
                    Optional.ofNullable(grade.getGrade()).ifPresent(existingGrade::setGrade);

                    if (grade.getAssignment() != null) {
                        Assignment assignment = assignmentRepository.findById(grade.getAssignment().getId()).orElseThrow(() -> new RuntimeException("Assignment not found"));
                        existingGrade.setAssignment(assignment);
                        existingGrade.setExam(null);
                    } else {
                        if (grade.getExam() == null) {
                            throw new RuntimeException("Exam not found");
                        }
                        Exam exam = examRepository.findById(grade.getExam().getId()).orElseThrow(() -> new RuntimeException("Exam not found"));
                        existingGrade.setExam(exam);
                        existingGrade.setAssignment(null);
                    }

                    existingGrade.setStudent(student);
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
