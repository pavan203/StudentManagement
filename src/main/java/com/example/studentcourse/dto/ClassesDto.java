package com.example.studentcourse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassesDto {

    private String className;
    private String teacherName;
    private Integer noOfStudents;
}
