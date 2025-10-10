package com.example.studentcourse.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Classes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id")
    private Long id;
    @Column(name = "class_name", nullable = false)
    private String className;
    @Column(name="teacher_name",nullable = false)
    private String teacherName;
    @Column(name = "no_of_students")
    private Integer noOfStudents;
    @OneToMany(mappedBy = "classEnrolled",cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<Student> students;

}
