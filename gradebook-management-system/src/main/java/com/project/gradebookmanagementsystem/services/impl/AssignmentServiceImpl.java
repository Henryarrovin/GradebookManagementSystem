package com.project.gradebookmanagementsystem.services.impl;

import com.project.gradebookmanagementsystem.models.Assignment;
import com.project.gradebookmanagementsystem.models.Course;
import com.project.gradebookmanagementsystem.repositories.AssignmentRepository;
import com.project.gradebookmanagementsystem.repositories.CourseRepository;
import com.project.gradebookmanagementsystem.services.AssignmentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    private AssignmentRepository assignmentRepository;
    private CourseRepository courseRepository;

    public AssignmentServiceImpl(AssignmentRepository assignmentRepository, CourseRepository courseRepository) {
        this.assignmentRepository = assignmentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public Assignment createAssignment(Assignment assignment) {
        Course course = courseRepository.findById(assignment.getCourse().getId()).orElse(null);
        if (course == null) {
            throw new RuntimeException("Course not found");
        }
        Assignment newAssignment = new Assignment();
        newAssignment.setTitle(assignment.getTitle());
        newAssignment.setDescription(assignment.getDescription());
        newAssignment.setDueDate(assignment.getDueDate());
        newAssignment.setCourse(course);

        return assignmentRepository.save(newAssignment);
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
        Course course = courseRepository.findById(assignment.getCourse().getId()).orElse(null);
        if (course == null) {
            throw new RuntimeException("Course not found");
        }
        return assignmentRepository.findById(id)
                .map(existingAssignment -> {
                    Optional.ofNullable(assignment.getTitle()).ifPresent(existingAssignment::setTitle);
                    Optional.ofNullable(assignment.getDescription()).ifPresent(existingAssignment::setDescription);
                    Optional.ofNullable(assignment.getDueDate()).ifPresent(existingAssignment::setDueDate);
                    existingAssignment.setCourse(course);
                    return assignmentRepository.save(existingAssignment);
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
