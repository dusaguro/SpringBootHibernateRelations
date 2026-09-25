package com.dsgr.springboot.springboot_jpa_relations.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.dsgr.springboot.springboot_jpa_relations.entities.Student;

public interface StudentRepository extends CrudRepository<Student, Long> {

    @Query ("SELECT s FROM Student s LEFT JOIN FETCH s.courses WHERE s.id = :id")
    Optional<Student> findOne(Long id);

}
