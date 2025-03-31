package com.vjezba.Sustav_upisa_studenata.Enrollment;

import com.vjezba.Sustav_upisa_studenata.Course.Course;
import com.vjezba.Sustav_upisa_studenata.Student.Student;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EnrollmentMapper {

    public Enrollment toEnrollment(EnrollmentDTO dto,Student student, Course course) {
        // Ako student ne postoji, kreiraj ga
        if (student == null) {
            throw new IllegalArgumentException("Student with email " + dto.email() + " does not exist.");
        }

        // Ako predmet ne postoji, kreiraj ga
        if (course == null) {
            throw new IllegalArgumentException("Course with name " + dto.courseName() + " does not exist.");
        }

        // Kreiraj Enrollment entitet
        return new Enrollment(student, course, LocalDate.now());
    }

    public EnrollmentResponseDTO toEnrollmentResponseDTO(Enrollment enrollment) {
        return new EnrollmentResponseDTO(
                enrollment.getStudent().getFirstName(),
                enrollment.getStudent().getLastName(),
                enrollment.getStudent().getEmail(),
                enrollment.getStudent().getBirthDate(),
                enrollment.getCourse().getName(),
                enrollment.getEnrollmentDate()
        );
    }


}
