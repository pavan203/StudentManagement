package com.example.studentcourse.service;

import com.example.studentcourse.dto.ClassesDto;
import com.example.studentcourse.dto.StudentDto;
import com.example.studentcourse.mapper.ClassesMapper;
import com.example.studentcourse.mapper.StudentMapper;
import com.example.studentcourse.model.Classes;
import com.example.studentcourse.model.Student;
import com.example.studentcourse.repository.ClassesRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ClassService {
    @Autowired
    ClassesRepository classesRepository;
    @Autowired
    ClassesMapper classesMapper;
    @Autowired
    EntityManager entityManager;
    @Autowired
    StudentMapper studentMapper;
    public ResponseEntity<List<ClassesDto>> getAllClasses() {
        //classesRepository.updatStudentCount();
        List<Classes> classList = classesRepository.findAll();

        if(classList.isEmpty()) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok(classList.stream()
                                        .map(c->classesMapper.toDto(c))
                                            .toList());
    }

    public ResponseEntity<ClassesDto> getClassById(long id) {

        /*Optional<Classes> classes= classesRepository.findById(id);
        if (classes.isEmpty()) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok(classes.get());*/
        return classesRepository.findById(id)
                .map(c->ResponseEntity.ok(classesMapper.toDto(c)))//c->ResponseEntity.ok(c)
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<?> removeClassById(Long id) {
        if (classesRepository.existsById(id)) {
            classesRepository.deleteById(id);
            return ResponseEntity.ok("deleted");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("doesn't exist");
    }

    public ResponseEntity<List<Classes>> getAllClassesNormal() {
        List<Classes> classList = classesRepository.findAll();
        if(classList.isEmpty()) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok(classList);
    }

    public ResponseEntity<List<StudentDto>> getStudentList(Long id) {
        Classes classes= entityManager.find(Classes.class,id);
        if (classes.getStudents().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<StudentDto> studentDtoList=classes.getStudents().stream().map(s->studentMapper.toDto(s)).toList();
        return ResponseEntity.ok(studentDtoList);
    }

    public ResponseEntity<?> dynamicSearch(Long cId, Integer noOfStudents, Long sId){
        CriteriaBuilder cb= entityManager.getCriteriaBuilder();
        CriteriaQuery<ClassesDto> cq= cb.createQuery(ClassesDto.class);
        Root<Classes> classesRoot= cq.from(Classes.class);
        Join<Classes,Student> studentJoin= classesRoot.join("students");
        List<Predicate> predicates=new ArrayList<>();
        if(cId!=null) predicates.add(cb.equal(classesRoot.get("id"),cId));
        if(noOfStudents!=null) predicates.add(cb.greaterThan(classesRoot.get("noOfStudents"),noOfStudents));
        if(sId!=null) predicates.add(cb.greaterThan(studentJoin.get("id"),sId));
        Predicate[] pArray=predicates.toArray(new Predicate[0]);
        cq.select(cb.construct(ClassesDto.class,classesRoot
                        .get("className"),classesRoot.get("teacherName"),classesRoot.get("noOfStudents")))
                            .where(cb.and(pArray));

        return ResponseEntity.ok(entityManager.createQuery(cq).getResultList());
    }

}
