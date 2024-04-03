package com.project.gradebookmanagementsystem.dtos;

import com.project.gradebookmanagementsystem.models.Teacher;
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
public class CourseDto {

    private Long id;
    private String name;
    private Teacher teacher;

}
