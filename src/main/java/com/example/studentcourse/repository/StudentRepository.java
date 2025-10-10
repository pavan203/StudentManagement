package com.example.studentcourse.repository;

import com.example.studentcourse.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("select count(s),s.classEnrolled.id from Student s group by s.classEnrolled.id")
    public List<Object[]> countStudentByClass();
}
