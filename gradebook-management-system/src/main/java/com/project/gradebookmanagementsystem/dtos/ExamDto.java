package com.project.gradebookmanagementsystem.dtos;

import com.project.gradebookmanagementsystem.models.Course;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamDto {

    private Long id;
    private String title;
    private String description;
    private LocalDate examDate;
    private Course course;

}
