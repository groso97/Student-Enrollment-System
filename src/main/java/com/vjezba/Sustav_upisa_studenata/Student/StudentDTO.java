package com.vjezba.Sustav_upisa_studenata.Student;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record StudentDTO(
        @NotBlank(message = "First name cannot be blank")
        @Size(min = 2, max = 20, message = "First name must be between 2 and 20 characters.")
        @Pattern(regexp = "^[A-Za-z]+$", message = "First name can only contain letters.")
        String firstName,

        @NotBlank(message = "Last name cannot be blank.")
        @Size(min = 2, max = 20, message = "Last name must be between 2 and 20 characters.")
        @Pattern(regexp = "^[A-Za-z]+$", message = "Last name can only contain letters.")
        String lastName,

        @NotBlank(message = "Email cannot be blank.")
        @Email(message = "Invalid email format. Example: john.doe@example.com")
        String email,

        @NotNull(message = "Birth date cannot be null.")
        @Past(message = "Birth date must be a past date.")
        LocalDate birthDate
) {
}
