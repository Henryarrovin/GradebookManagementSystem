package com.project.gradebookmanagementsystem.services.impl;


import com.project.gradebookmanagementsystem.models.Course;
import com.project.gradebookmanagementsystem.models.Exam;
import com.project.gradebookmanagementsystem.repositories.CourseRepository;
import com.project.gradebookmanagementsystem.repositories.ExamRepository;
import com.project.gradebookmanagementsystem.services.ExamService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExamServiceImpl implements ExamService {

    private ExamRepository examRepository;
    private CourseRepository courseRepository;

    public ExamServiceImpl(ExamRepository examRepository, CourseRepository courseRepository) {
        this.examRepository = examRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public Exam createExam(Exam exam) {
        Course course = courseRepository.findById(exam.getCourse().getId()).orElse(null);
        if (course == null) {
            throw new RuntimeException("Course not found");
        }
        Exam newExam = new Exam();
        newExam.setTitle(exam.getTitle());
        newExam.setDescription(exam.getDescription());
        newExam.setExamDate(exam.getExamDate());
        newExam.setCourse(course);

        return examRepository.save(newExam);
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
        Course course = courseRepository.findById(exam.getCourse().getId()).orElse(null);
        if (course == null) {
            throw new RuntimeException("Course not found");
        }
        return examRepository.findById(id)
                .map(existingExam -> {
                    Optional.ofNullable(exam.getTitle()).ifPresent(existingExam::setTitle);
                    Optional.ofNullable(exam.getDescription()).ifPresent(existingExam::setDescription);
                    Optional.ofNullable(exam.getExamDate()).ifPresent(existingExam::setExamDate);
                    existingExam.setCourse(course);
                    return examRepository.save(existingExam);
                }).orElseThrow(() -> new RuntimeException("Exam not found"));
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
