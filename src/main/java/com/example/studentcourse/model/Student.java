package com.example.studentcourse.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Student {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name="student_id")
private Long id;
@Column(name = "student_name", nullable = false, length = 100)
private String name;
@Column(name = "roll_number", nullable = false)
private Integer rollNo;
@ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name="class_id")//just to write the column name if omitted default name will be assigned
//@JsonBackReference// avoids looping of the many-one relation mapping
@JsonIgnoreProperties("students")
private Classes classEnrolled;
}
