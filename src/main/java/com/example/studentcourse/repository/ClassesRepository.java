package com.example.studentcourse.repository;

import com.example.studentcourse.model.Classes;
import jakarta.persistence.NamedQuery;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClassesRepository extends JpaRepository<Classes,Long> {
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value= "update classes c join ( select class_id,count(*) as ct from student GROUP BY class_id) s on c.class_id= s.class_id set c.no_of_students=coalesce(s.ct,0);", nativeQuery = true)
    public void updatStudentCount();


}

