package com.example.studentcourse.mapper;

import com.example.studentcourse.dto.ClassesDto;
import com.example.studentcourse.dto.StudentDto;
import com.example.studentcourse.model.Classes;
import com.example.studentcourse.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface ClassesMapper {
    @Mapping(source = "noOfStudents", target="noOfStudents")
    ClassesDto toDto(Classes classes);
    Classes toEntity(ClassesDto dto);
}
