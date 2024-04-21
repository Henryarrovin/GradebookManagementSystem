package com.project.gradebookmanagementsystem.controllers;

import com.project.gradebookmanagementsystem.dtos.ExamDto;
import com.project.gradebookmanagementsystem.mappers.Mapper;
import com.project.gradebookmanagementsystem.models.Exam;
import com.project.gradebookmanagementsystem.services.ExamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/exams")
@CrossOrigin(origins = "http://localhost:5173")
public class ExamController {

    private ExamService examService;
    private Mapper<Exam, ExamDto> examMapper;

    public ExamController(ExamService examService, Mapper<Exam, ExamDto> examMapper) {
        this.examService = examService;
        this.examMapper = examMapper;
    }

    @PostMapping("/create-exam")
    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR', 'TEACHER')")
    public ResponseEntity<ExamDto> createExam(@RequestBody ExamDto examDto) {

        if (examDto.getTitle() == null ||
                examDto.getDescription() == null ||
                examDto.getExamDate() == null ||
                examDto.getCourse() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            Exam exam = examMapper.mapFrom(examDto);
            Exam examCreated = examService.createExam(exam);
            return new ResponseEntity<>(examMapper.mapTo(examCreated), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("update-exam/{id}")
    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR', 'TEACHER')")
    public ResponseEntity<ExamDto> updateExam(@PathVariable Long id, @RequestBody ExamDto examDto) {

        if (!examService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            Exam exam = examMapper.mapFrom(examDto);
            Exam updatedExam = examService.updateExam(id, exam);
            return new ResponseEntity<>(examMapper.mapTo(updatedExam), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-all-exams")
    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR', 'TEACHER', 'STUDENT')")
    public ResponseEntity<Iterable<ExamDto>> getAllExams() {
        Iterable<Exam> exams = examService.getAllExams();
        List<ExamDto> examDto = new ArrayList<>();
        for (Exam exam : exams) {
            examDto.add(examMapper.mapTo(exam));
        }
        return new ResponseEntity<>(examDto, HttpStatus.OK);
    }

    @GetMapping("/get-exam/{id}")
    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR', 'TEACHER', 'STUDENT')")
    public ResponseEntity<ExamDto> getExamById(@PathVariable Long id) {
        Exam exam = examService.getExamById(id);

        if (!examService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(examMapper.mapTo(exam), HttpStatus.OK);
    }

    @DeleteMapping("/delete-exam/{id}")
    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR', 'TEACHER')")
    public ResponseEntity<Void> deleteExam(@PathVariable Long id) {
        examService.deleteExam(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
