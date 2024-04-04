package com.project.gradebookmanagementsystem.mappers.impl;

import com.project.gradebookmanagementsystem.dtos.CourseDto;
import com.project.gradebookmanagementsystem.mappers.Mapper;
import com.project.gradebookmanagementsystem.models.Course;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CourseMapperImpl implements Mapper<Course, CourseDto> {

    private ModelMapper modelMapper;

    public CourseMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CourseDto mapTo(Course course) {
        return modelMapper.map(course, CourseDto.class);
    }

    @Override
    public Course mapFrom(CourseDto courseDto) {
        return modelMapper.map(courseDto, Course.class);
    }
}
