package com.project.gradebookmanagementsystem.services;

import com.project.gradebookmanagementsystem.models.Assignment;

import java.util.List;

public interface AssignmentService {

    List<Assignment> getAllAssignments();

    Assignment getAssignmentById(Long id);

    Assignment createAssignment(Assignment assignment);

    Assignment updateAssignment(Long id, Assignment assignment);

    void deleteAssignment(Long id);

}
