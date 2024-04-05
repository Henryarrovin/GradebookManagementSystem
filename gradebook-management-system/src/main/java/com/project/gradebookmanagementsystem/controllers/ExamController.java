package com.project.gradebookmanagementsystem.controllers;

import com.project.gradebookmanagementsystem.dtos.ExamDto;
import com.project.gradebookmanagementsystem.mappers.Mapper;
import com.project.gradebookmanagementsystem.models.Exam;
import com.project.gradebookmanagementsystem.services.ExamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/exam")
public class ExamController {

    private ExamService examService;
    private Mapper<Exam, ExamDto> examMapper;

    public ExamController(ExamService examService, Mapper<Exam, ExamDto> examMapper) {
        this.examService = examService;
        this.examMapper = examMapper;
    }

    @PostMapping("/create-exam")
    public ResponseEntity<ExamDto> createExam(@RequestBody ExamDto examDto) {

        if (examDto.getTitle() == null ||
                examDto.getDescription() == null ||
                examDto.getExamDate() == null ||
                examDto.getCourse() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Exam exam = examMapper.mapFrom(examDto);
        Exam examCreated = examService.createExam(exam);
        return new ResponseEntity<>(examMapper.mapTo(examCreated), HttpStatus.CREATED);
    }

    @PutMapping("update-exam/{id}")
    public ResponseEntity<ExamDto> updateExam(@PathVariable Long id, @RequestBody ExamDto examDto) {

        if (!examService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Exam exam = examMapper.mapFrom(examDto);
        Exam updatedExam = examService.updateExam(id, exam);
        return new ResponseEntity<>(examMapper.mapTo(updatedExam), HttpStatus.OK);
    }

    @GetMapping("/get-all-exams")
    public ResponseEntity<Iterable<ExamDto>> getAllExams() {
        Iterable<Exam> exams = examService.getAllExams();
        List<ExamDto> examDto = new ArrayList<>();
        for (Exam exam : exams) {
            examDto.add(examMapper.mapTo(exam));
        }
        return new ResponseEntity<>(examDto, HttpStatus.OK);
    }

    @GetMapping("/get-exam/{id}")
    public ResponseEntity<ExamDto> getExamById(@PathVariable Long id) {
        Exam exam = examService.getExamById(id);

        if (!examService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(examMapper.mapTo(exam), HttpStatus.OK);
    }
}
