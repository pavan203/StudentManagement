package com.example.studentcourse.controller;

import com.example.studentcourse.dto.ClassesDto;
import com.example.studentcourse.dto.StudentDto;
import com.example.studentcourse.model.Classes;
import com.example.studentcourse.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("class")
public class ClassController {
    @Autowired
    ClassService classService;
    @GetMapping("/all")
    public ResponseEntity<List<ClassesDto>> getAllClasses(){
        return classService.getAllClasses();
    }
    @GetMapping("/alll")
    public ResponseEntity<List<Classes>> getAllClassesNormak(){
        return classService.getAllClassesNormal();
    }
    @GetMapping("/{id}")
    public ResponseEntity<ClassesDto> getClassById(@PathVariable long id){
        return classService.getClassById(id);
    }
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> removeClassById(@PathVariable Long id){
        return  classService.removeClassById(id);
    }
    @GetMapping("/{id}/students")
    public ResponseEntity<List<StudentDto>> getStudentList(@PathVariable Long id){
        return classService.getStudentList(id);
    }
    @GetMapping("/dynamicSearch")
    public ResponseEntity<?> dynamicSearch(@RequestParam(required = false) Long id,
                                              @RequestParam(required = false) Integer no_of_students,
                                              @RequestParam(required = false) Long sId){

        return classService.dynamicSearch(id,no_of_students,sId);
    }

}
