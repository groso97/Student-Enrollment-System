package com.vjezba.Sustav_upisa_studenata.Enrollment;


import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record EnrollmentDTO(
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
        LocalDate birthDate,

        @NotBlank(message = "Course name cannot be blank.")
        @Size(min = 2,max = 20, message = "Course name must be between 2 and 20 characters." )
        @Pattern(regexp = "^[A-Za-z]+$", message = "Course name can only contain letters.")
        String courseName,

        @NotNull(message = "Enrollment date cannot be null.")
        @FutureOrPresent(message = "Enrollment date must be today or in the future.")
        LocalDate enrollmentDate
) { }
