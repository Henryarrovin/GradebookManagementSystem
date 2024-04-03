package com.project.gradebookmanagementsystem.dtos;

import com.project.gradebookmanagementsystem.models.Assignment;
import com.project.gradebookmanagementsystem.models.Exam;
import com.project.gradebookmanagementsystem.models.Student;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GradeDto {

    private Long id;
    private Student student;
    private Assignment assignment;
    private Exam exam;
    private double grade;

}
