package com.project.gradebookmanagementsystem.mappers.impl;

import com.project.gradebookmanagementsystem.dtos.TeacherDto;
import com.project.gradebookmanagementsystem.mappers.Mapper;
import com.project.gradebookmanagementsystem.models.Teacher;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TeacherMapperImpl implements Mapper<Teacher, TeacherDto> {

    private ModelMapper modelMapper;
    public TeacherMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public TeacherDto mapTo(Teacher teacher) {
        return modelMapper.map(teacher, TeacherDto.class);
    }

    @Override
    public Teacher mapFrom(TeacherDto teacherDto) {
        return modelMapper.map(teacherDto, Teacher.class);
    }
}
