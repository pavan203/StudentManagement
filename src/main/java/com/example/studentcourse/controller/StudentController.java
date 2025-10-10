package com.example.studentcourse.controller;

import com.example.studentcourse.dto.StudentDto;
import com.example.studentcourse.model.Student;
import com.example.studentcourse.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@RestController
@RequestMapping("student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @GetMapping("/")
    public String getStudent() {
        return "helo  p@1";
    }

    @GetMapping("/all")
    public List<StudentDto> getStudentList() {
        return studentService.getSTudent();
    }

    @GetMapping("/alll")
    public ResponseEntity<List<Student>> getStudentListNormal() {
        return studentService.getSTudentNormal();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> removeStudentById(@PathVariable Long id) {
        return studentService.removeStudentById(id);
    }

    @GetMapping("/{id}/class")
    public ResponseEntity<?> getClassOfStudent(@PathVariable Long id) {
        return studentService.getClassOfStudent(id);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addStudent(@RequestBody StudentDto studentDto){
        return studentService.addStudent(studentDto);
    }

    @PostMapping("/enroll")
    public ResponseEntity<?> saveStudentBatch(@RequestBody List<StudentDto> studentDtoList) {
        return studentService.addStudentBatch(studentDtoList);
    }
    @GetMapping("/{id}/details")
    public ResponseEntity<?> getStudentDetails(@PathVariable Long id){
        return studentService.getStudentDetails(id);
    }

}
