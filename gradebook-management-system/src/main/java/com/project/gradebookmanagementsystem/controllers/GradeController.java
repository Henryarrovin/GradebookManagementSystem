package com.project.gradebookmanagementsystem.controllers;

import com.project.gradebookmanagementsystem.dtos.GradeDto;
import com.project.gradebookmanagementsystem.mappers.Mapper;
import com.project.gradebookmanagementsystem.models.Grade;
import com.project.gradebookmanagementsystem.services.GradeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/grades")
@CrossOrigin(origins = "http://localhost:5173")
public class GradeController {

    private GradeService gradeService;
    private Mapper<Grade, GradeDto> gradeMapper;

    public GradeController(GradeService gradeService, Mapper<Grade, GradeDto> gradeMapper) {
        this.gradeService = gradeService;
        this.gradeMapper = gradeMapper;
    }

    @PostMapping("/create-grade")
    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR', 'TEACHER')")
    public ResponseEntity<?> createGrade(@RequestBody GradeDto gradeDto) {
        try {
            Grade grade = gradeMapper.mapFrom(gradeDto);
            Grade createdGrade = gradeService.createGrade(grade);
            return new ResponseEntity<>(gradeMapper.mapTo(createdGrade), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage() ,HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/update-grade/{id}")
    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR', 'TEACHER')")
    public ResponseEntity<?> updateGrade(@PathVariable Long id, @RequestBody GradeDto gradeDto) {
        if (!gradeService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            Grade grade = gradeMapper.mapFrom(gradeDto);
            Grade updatedGrade = gradeService.updateGrade(id, grade);
            return new ResponseEntity<>(gradeMapper.mapTo(updatedGrade), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage() ,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-all-grades")
    public ResponseEntity<Iterable<GradeDto>> getAllGrades() {
        Iterable<Grade> grades = gradeService.getAllGrades();
        List<GradeDto> gradeDto = new ArrayList<>();
        for (Grade grade : grades) {
            gradeDto.add(gradeMapper.mapTo(grade));
        }
        return new ResponseEntity<>(gradeDto, HttpStatus.OK);
    }

    @GetMapping("/get-grade/{id}")
    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR', 'TEACHER', 'STUDENT')")
    public ResponseEntity<GradeDto> getGradeById(@PathVariable Long id) {
        Grade grade = gradeService.getGradeById(id);
        if (!gradeService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(gradeMapper.mapTo(grade), HttpStatus.OK);
    }

    @DeleteMapping("/delete-grade/{id}")
    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR', 'TEACHER')")
    public ResponseEntity<GradeDto> deleteGrade(@PathVariable Long id) {
        gradeService.deleteGrade(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
