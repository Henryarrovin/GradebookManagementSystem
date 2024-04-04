package com.project.gradebookmanagementsystem.mappers.impl;

import com.project.gradebookmanagementsystem.dtos.AssignmentDto;
import com.project.gradebookmanagementsystem.mappers.Mapper;
import com.project.gradebookmanagementsystem.models.Assignment;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AssignmentMapperImpl implements Mapper<Assignment, AssignmentDto> {

    private ModelMapper modelMapper;

    public AssignmentMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AssignmentDto mapTo(Assignment assignment) {
        return modelMapper.map(assignment, AssignmentDto.class);
    }

    @Override
    public Assignment mapFrom(AssignmentDto assignmentDto) {
        return modelMapper.map(assignmentDto, Assignment.class);
    }
}
