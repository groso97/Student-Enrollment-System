package com.vjezba.Sustav_upisa_studenata.Enrollment;

import java.time.LocalDate;

public record EnrollmentResponseDTO(
        String firstName,
        String lastName,
        String email,
        LocalDate birthDate,
        String courseName,
        LocalDate enrollmentDate
) {
}
