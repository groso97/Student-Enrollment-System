package com.vjezba.Sustav_upisa_studenata.Course;

import jakarta.validation.constraints.*;

public record CourseDTO(
        @NotBlank(message = "Course name cannot be blank.")
        @Size(min = 2,max = 20, message = "Course name must be between 2 and 20 characters." )
        @Pattern(regexp = "^[A-Za-z]+$", message = "Course name can only contain letters.")
        String name,

        @NotBlank(message = "Description cannot be blank.")
        @Size(min = 20, max=50, message = "Course name must be between 2 and 20 characters.")
        String description,

        @NotNull(message = "Ects value cannot be null.")
        @Min(value = 2, message = "ECTS value must be at least 2.")
        @Max(value = 7, message = "ECTS value cannot be greater than 7.")
        @Digits(integer = 1, fraction = 0, message = "ECTS must be a whole number between 2 and 7.")
        int ects
) {
}
