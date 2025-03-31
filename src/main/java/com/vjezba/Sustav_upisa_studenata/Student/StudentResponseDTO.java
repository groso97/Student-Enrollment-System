package com.vjezba.Sustav_upisa_studenata.Student;

import java.time.LocalDate;

public record StudentResponseDTO(
        String firstName,
        String lastName,
        String email,
        LocalDate birthDate
) {
}
