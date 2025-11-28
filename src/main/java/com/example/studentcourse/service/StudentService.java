package com.example.studentcourse.service;

import com.example.studentcourse.dto.StudentDto;
import com.example.studentcourse.mapper.StudentMapper;
import com.example.studentcourse.model.Classes;
import com.example.studentcourse.model.Student;
import com.example.studentcourse.repository.ClassesRepository;
import com.example.studentcourse.repository.StudentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    ClassesRepository classesRepository;
    @Autowired
    private EntityManager entityManager;

    public ResponseEntity<StudentDto> getStudentById(Long id) {
        //Student s=studentRepository.findById(id).get();
        return studentRepository.findById(id)
                .map(s->ResponseEntity.ok(studentMapper.toDto(s)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Cacheable(value = "studentCountByClass")
    public List<Object[]> getStudentCountByClass() {
        return studentRepository.countStudentByClass();
    }
    @Cacheable(value = "studentListCache")
    public List<StudentDto> getSTudent() {
        List<Student> studentList= studentRepository.findAll();
        if(studentList.isEmpty()) return null;
        else
            return studentList.stream()
                .map(s->studentMapper.toDto(s))
                    .toList();
    }

    public ResponseEntity<List<Student>> getSTudentNormal() {
        List<Student> studentList= studentRepository.findAll();
        if(studentList.isEmpty()) return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(studentList);
    }
    @Transactional

    @CacheEvict(value = "studentCountByClass", allEntries = true)

    public ResponseEntity<?> removeStudentById(Long id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            classesRepository.updatStudentCount();
            return ResponseEntity.ok("deleted");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("doesn't exist");
    }

    public ResponseEntity<?> getClassOfStudent(Long id) {
        Optional<Student> student=studentRepository.findById(id);
        if (student.isPresent()){
            Classes classes= student.get().getClassEnrolled();
            return ResponseEntity.ok(classes.getClassName()+" "+classes.getId());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no such student exist");
    }
    @Transactional
    @CacheEvict(value = "studentCountByClass", allEntries = true)
    public ResponseEntity<?> addStudent(StudentDto studentDto) {

            Student student=studentMapper.toEntity(studentDto);
            Optional<Classes> classes= classesRepository.findById(studentDto.getClassId());
            if (classes.isEmpty())
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("class error");
            student.setClassEnrolled(classes.get());

            studentRepository.save(student);
        classesRepository.updatStudentCount();
        return ResponseEntity.ok("added");
    }
    @Transactional
    @CacheEvict(value = "studentCountByClass", allEntries = true)
    public ResponseEntity<?> addStudentBatch(List<StudentDto> studentListDto) {

        for (StudentDto studentDto : studentListDto) {
            Student student = studentMapper.toEntity(studentDto);

            if (entityManager.find(Classes.class, studentDto.getClassId()) == null) {
                throw new RuntimeException("invalid class id: " + studentDto.getClassId());
            }
            System.out.println(entityManager.find(Classes.class, studentDto.getClassId()) );// example of 1L casching thatoccirs in @trans
            Classes classes = entityManager.find(Classes.class, studentDto.getClassId());
            student.setClassEnrolled(classes);
            classes.setNoOfStudents(classes.getNoOfStudents()+1);
            entityManager.merge(classes);
            entityManager.persist(student);
        }

        //classesRepository.updatStudentCount();
        return ResponseEntity.ok("added all");
    }


    public ResponseEntity<?> getStudentDetails(Long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        // select * from student where id= id
        CriteriaQuery<Object[]> cq= cb.createQuery(Object[].class);// defines return type
        Root<Student> root = cq.from(Student.class);// from student
        cq.multiselect(root.get("id"),root.get("name"))
                .where(cb.equal(root.get("id"),id));
        //System.out.println(cq.select(root).getClass());
        List<Object[]> studentList=entityManager.createQuery(cq).getResultList();

        return ResponseEntity.ok(studentList);
    }
}

