package com.project.gradebookmanagementsystem.services.impl;

import com.project.gradebookmanagementsystem.models.Assignment;
import com.project.gradebookmanagementsystem.repositories.AssignmentRepository;
import com.project.gradebookmanagementsystem.services.AssignmentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    private AssignmentRepository assignmentRepository;

    public AssignmentServiceImpl(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    @Override
    public Assignment createAssignment(Assignment assignment) {
        return assignmentRepository.save(assignment);
    }
    @Override
    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    @Override
    public Assignment getAssignmentById(Long id) {
        return assignmentRepository.findById(id).orElse(null);
    }

    @Override
    public Assignment updateAssignment(Long id, Assignment assignment) {
        assignment.setId(id);
        return assignmentRepository.findById(id)
                .map(existingTeacher -> {
                    Optional.ofNullable(assignment.getTitle()).ifPresent(existingTeacher::setTitle);
                    Optional.ofNullable(assignment.getDescription()).ifPresent(existingTeacher::setDescription);
                    Optional.ofNullable(assignment.getDueDate()).ifPresent(existingTeacher::setDueDate);
                    Optional.ofNullable(assignment.getCourse()).ifPresent(existingTeacher::setCourse);
                    return assignmentRepository.save(existingTeacher);
                }).orElseThrow(() -> new RuntimeException("Assignment not found"));
    }

    @Override
    public void deleteAssignment(Long id) {
        assignmentRepository.deleteById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return assignmentRepository.existsById(id);
    }
}
