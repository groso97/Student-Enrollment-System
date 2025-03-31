package com.vjezba.Sustav_upisa_studenata.Course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    boolean existsByName(String name);
    Optional<Course> findByName(String name);
}
