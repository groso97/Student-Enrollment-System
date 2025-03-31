package com.vjezba.Sustav_upisa_studenata.Course;

import org.springframework.stereotype.Service;

@Service
public class CourseMapper {

    public Course toCourse(CourseDTO courseDTO) {
        Course course = new Course();
        course.setName(courseDTO.name());
        course.setDescription(courseDTO.description());
        course.setECTS(courseDTO.ects());

        return course;
    }

    public CourseResponseDTO toCourseResponseDTO(Course course) {
        return new CourseResponseDTO(
                course.getName(),
                course.getDescription(),
                course.getECTS()
        );
    }
}
