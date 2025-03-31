package com.vjezba.Sustav_upisa_studenata.Course;

import com.vjezba.Sustav_upisa_studenata.Enrollment.Enrollment;
import com.vjezba.Sustav_upisa_studenata.Enrollment.EnrollmentRepository;
import com.vjezba.Sustav_upisa_studenata.Student.Student;
import com.vjezba.Sustav_upisa_studenata.Student.StudentMapper;
import com.vjezba.Sustav_upisa_studenata.Student.StudentResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {
    private static final Logger log = LoggerFactory.getLogger(CourseService.class);
    public final CourseRepository courseRepository;
    public final EnrollmentRepository enrollmentRepository;
    public final CourseMapper courseMapper;
    public final StudentMapper studentMapper;

    public CourseService(CourseRepository courseRepository, EnrollmentRepository enrollmentRepository, CourseMapper courseMapper, StudentMapper studentMapper) {
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.courseMapper = courseMapper;
        this.studentMapper = studentMapper;
    }

    public List<CourseResponseDTO> findAllCourses(){
        List<CourseResponseDTO> courses = courseRepository.findAll()
                .stream()
                .map(courseMapper::toCourseResponseDTO)
                .collect(Collectors.toList());
        if (courses.isEmpty()) {
            log.warn("No courses found in the database.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No courses found in the database.");
        }
        return courses;
    }

    public List<StudentResponseDTO> getStudentsForCourse(int courseId){
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course with ID: " + courseId + " not found"));

        List<Enrollment> enrollments = enrollmentRepository.findByCourseId(courseId);

        if (enrollments.isEmpty()) {
            log.warn("No students found for course with ID: {}", courseId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No students enrolled in the course with ID: " + courseId);
        }

        List<Student> students = enrollments.stream()
                .map(Enrollment::getStudent)
                .collect(Collectors.toList());

        return students.stream()
                .map(studentMapper::toStudentResponseDTO)
                .collect(Collectors.toList());
    }

    public CourseResponseDTO saveCourse(CourseDTO courseDTO) {
        if(courseRepository.existsByName(courseDTO.name())){
            log.error("Attempt to register course with existing name: {}", courseDTO.name());
            throw new IllegalArgumentException("Course with the same name is already in use.");
        }
        Course course = courseMapper.toCourse(courseDTO);
        Course savedCourse = courseRepository.save(course);
        return courseMapper.toCourseResponseDTO(savedCourse);
    }

    public void deleteCourse(int id){
        if(!courseRepository.existsById(id)){
            log.error("Failed to delete: Course with id {} not found", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course with id: " + id + " not found");
        }
        courseRepository.deleteById(id);
    }

    public CourseResponseDTO findCourseById(int id) {
        return courseRepository.findById(id)
                .map(courseMapper::toCourseResponseDTO)
                .orElseThrow(()-> {
                    log.error("Course with id {} not found", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Course with id: " + id + " not found");

                });
    }
}
