
package com.example.studentcourse.mapper;

import com.example.studentcourse.model.Student;
import com.example.studentcourse.dto.StudentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    @Mapping(source = "classEnrolled.id", target="classId")
    StudentDto toDto(Student student);
    Student toEntity(StudentDto dto);
}
