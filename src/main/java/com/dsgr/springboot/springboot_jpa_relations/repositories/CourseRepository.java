package com.dsgr.springboot.springboot_jpa_relations.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.dsgr.springboot.springboot_jpa_relations.entities.Course;

public interface CourseRepository extends CrudRepository<Course, Long> {

    @Query ("SELECT c FROM Course c LEFT JOIN FETCH c.students WHERE c.id = :id")
    Optional<Course> findOne(Long id);

}
