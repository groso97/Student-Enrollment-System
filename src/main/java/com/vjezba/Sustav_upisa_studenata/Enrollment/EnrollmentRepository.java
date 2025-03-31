package com.vjezba.Sustav_upisa_studenata.Enrollment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {
    List<Enrollment> findEnrollmentByStudentId(int studentId);
    List<Enrollment> findByCourseId(int courseId);
}
