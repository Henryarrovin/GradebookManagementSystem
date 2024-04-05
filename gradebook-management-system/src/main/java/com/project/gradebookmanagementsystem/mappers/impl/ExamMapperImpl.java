package com.project.gradebookmanagementsystem.mappers.impl;

import com.project.gradebookmanagementsystem.dtos.ExamDto;
import com.project.gradebookmanagementsystem.mappers.Mapper;
import com.project.gradebookmanagementsystem.models.Exam;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExamMapperImpl implements Mapper<Exam, ExamDto> {

    private ModelMapper modelMapper;

    public ExamMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ExamDto mapTo(Exam exam) {
        return modelMapper.map(exam, ExamDto.class);
    }

    @Override
    public Exam mapFrom(ExamDto examDto) {
        return modelMapper.map(examDto, Exam.class);
    }
}
