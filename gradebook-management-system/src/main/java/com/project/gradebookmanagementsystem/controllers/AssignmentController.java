package com.project.gradebookmanagementsystem.controllers;

import com.project.gradebookmanagementsystem.dtos.AssignmentDto;
import com.project.gradebookmanagementsystem.mappers.Mapper;
import com.project.gradebookmanagementsystem.models.Assignment;
import com.project.gradebookmanagementsystem.services.AssignmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/assignment")
public class AssignmentController {

    private AssignmentService assignmentService;
    private Mapper<Assignment, AssignmentDto> assignmentMapper;

    public AssignmentController(AssignmentService assignmentService, Mapper<Assignment, AssignmentDto> assignmentMapper) {
        this.assignmentService = assignmentService;
        this.assignmentMapper = assignmentMapper;
    }

    @PostMapping("/create-assignment")
    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR', 'TEACHER')")
    public ResponseEntity<AssignmentDto> createAssignment(@RequestBody AssignmentDto assignmentDto) {

        if (assignmentDto.getTitle() == null ||
                assignmentDto.getDescription() == null||
                assignmentDto.getDueDate() == null||
                assignmentDto.getCourse() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            Assignment assignment = assignmentMapper.mapFrom(assignmentDto);
            Assignment assignmentCreated = assignmentService.createAssignment(assignment);
            return new ResponseEntity<>(assignmentMapper.mapTo(assignmentCreated), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("update-assignment/{id}")
    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR', 'TEACHER')")
    public ResponseEntity<?> updateAssignment(@PathVariable Long id, @RequestBody AssignmentDto assignmentDto) {

        if (!assignmentService.isExists(id)) {
            return new ResponseEntity<>("Assignment doesnt exist.",HttpStatus.NOT_FOUND);
        }

        try {
            Assignment assignment = assignmentMapper.mapFrom(assignmentDto);
            Assignment updatedAssignment = assignmentService.updateAssignment(id, assignment);
            return new ResponseEntity<>(assignmentMapper.mapTo(updatedAssignment), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get-all-assignments")
    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR', 'TEACHER', 'STUDENT')")
    public ResponseEntity<Iterable<AssignmentDto>> getAllAssignments() {
        Iterable<Assignment> assignments = assignmentService.getAllAssignments();
        List<AssignmentDto> assignmentDto = new ArrayList<>();
        for (Assignment assignment : assignments) {
            assignmentDto.add(assignmentMapper.mapTo(assignment));
        }
        return new ResponseEntity<>(assignmentDto, HttpStatus.OK);
    }

    @GetMapping("/get-assignment/{id}")
    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR', 'TEACHER', 'STUDENT')")
    public ResponseEntity<AssignmentDto> getAssignmentById(@PathVariable Long id) {

        Assignment assignment = assignmentService.getAssignmentById(id);

        if (!assignmentService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(assignmentMapper.mapTo(assignment), HttpStatus.OK);
    }

    @DeleteMapping("/delete-assignment/{id}")
    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR', 'TEACHER')")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long id) {
        assignmentService.deleteAssignment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
