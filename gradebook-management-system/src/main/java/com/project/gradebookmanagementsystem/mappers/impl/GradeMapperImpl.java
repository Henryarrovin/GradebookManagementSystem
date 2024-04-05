package com.project.gradebookmanagementsystem.mappers.impl;

import com.project.gradebookmanagementsystem.dtos.GradeDto;
import com.project.gradebookmanagementsystem.mappers.Mapper;
import com.project.gradebookmanagementsystem.models.Grade;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class GradeMapperImpl implements Mapper<Grade, GradeDto>{

    private ModelMapper modelMapper;

    public GradeMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public GradeDto mapTo(Grade grade) {
        return modelMapper.map(grade, GradeDto.class);
    }

    @Override
    public Grade mapFrom(GradeDto gradeDto) {
        return modelMapper.map(gradeDto, Grade.class);
    }
}
