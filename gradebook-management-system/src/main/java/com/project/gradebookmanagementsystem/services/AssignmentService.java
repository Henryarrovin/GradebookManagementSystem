package com.project.gradebookmanagementsystem.services;

import com.project.gradebookmanagementsystem.models.Assignment;

import java.util.List;

public interface AssignmentService {

    Assignment createAssignment(Assignment assignment);

    List<Assignment> getAllAssignments();

    Assignment getAssignmentById(Long id);

    Assignment updateAssignment(Long id, Assignment assignment);

    void deleteAssignment(Long id);

    boolean isExists(Long id);
}
