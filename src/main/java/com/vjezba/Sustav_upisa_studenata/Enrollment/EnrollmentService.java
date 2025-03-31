package com.vjezba.Sustav_upisa_studenata.Enrollment;


import com.vjezba.Sustav_upisa_studenata.Course.Course;
import com.vjezba.Sustav_upisa_studenata.Course.CourseRepository;
import com.vjezba.Sustav_upisa_studenata.Student.Student;
import com.vjezba.Sustav_upisa_studenata.Student.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnrollmentService {
    private static final Logger log = LoggerFactory.getLogger(EnrollmentService.class);
    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentMapper enrollmentMapper;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository, EnrollmentMapper enrollmentMapper, CourseRepository courseRepository, StudentRepository studentRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.enrollmentMapper = enrollmentMapper;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    public List<EnrollmentResponseDTO> findAllEnrollments(){
        List<EnrollmentResponseDTO> enrollments = enrollmentRepository.findAll()
                .stream()
                .map(enrollmentMapper::toEnrollmentResponseDTO)
                .collect(Collectors.toList());

        if (enrollments.isEmpty()) {
            log.warn("No enrollments found in the database.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No enrollments found in the database.");
        }

        return enrollments;
    }

    public EnrollmentResponseDTO findEnrollmentById(int id){
        return enrollmentRepository.findById(id)
                .map(enrollmentMapper::toEnrollmentResponseDTO)
                .orElseThrow(() -> {
                    log.error("Enrollment with id {} not found", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Enrollment with id: " + id + " not found");
                });
    }

    public List<EnrollmentResponseDTO> findEnrollmentByStudentId(int studentId){
        List<Enrollment> enrollments = enrollmentRepository.findEnrollmentByStudentId(studentId);

        if(enrollments.isEmpty()) {
            log.error("Student with ID {} has no enrollments.", studentId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student with ID " + studentId + " has no enrollments.");
        }
        return enrollments
                .stream()
                .map(enrollmentMapper::toEnrollmentResponseDTO)
                .collect(Collectors.toList());

    }

    public EnrollmentResponseDTO saveEnrollment(EnrollmentDTO enrollmentDTO){
        // Prvo, provjeri da li student postoji u bazi na osnovu email-a
        Student student = studentRepository.findByEmail(enrollmentDTO.email())
                .orElseThrow(() -> {
                    log.error("Student with email {} does not exist", enrollmentDTO.email());
                    return new IllegalArgumentException("Student with email " + enrollmentDTO.email() + " does not exist.");
                });


        // Provjeri da li se ime, prezime i datum rođenja poklapaju
        if (!student.getFirstName().equals(enrollmentDTO.firstName())) {
            log.error("First name does not match: expected {}, found {}", enrollmentDTO.firstName(), student.getFirstName());
            throw new IllegalArgumentException("First name does not match.");
        }
        if (!student.getLastName().equals(enrollmentDTO.lastName())) {
            log.error("Last name does not match: expected {}, found {}", enrollmentDTO.lastName(), student.getLastName());
            throw new IllegalArgumentException("Last name does not match.");
        }
        if (!student.getBirthDate().isEqual(enrollmentDTO.birthDate())) {
            log.error("Birth date does not match: expected {}, found {}", enrollmentDTO.birthDate(), student.getBirthDate());
            throw new IllegalArgumentException("Birth date does not match.");
        }

        // Zatim, provjeri da li predmet postoji u bazi na osnovu imena
        Course course = courseRepository.findByName(enrollmentDTO.courseName())
                .orElseThrow(() -> {
                    log.error("Course with name {} does not exist", enrollmentDTO.courseName());
                    return new IllegalArgumentException("Course with name " + enrollmentDTO.courseName() + " does not exist.");
                });


        // Kreiraj novi Enrollment objekt
        Enrollment enrollment = new Enrollment(student, course, enrollmentDTO.enrollmentDate());

        // Sačuvaj Enrollment u bazi
        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);

        // Vratimo DTO sa podacima o upisu
        return new EnrollmentResponseDTO(
                savedEnrollment.getStudent().getFirstName(),
                savedEnrollment.getStudent().getLastName(),
                savedEnrollment.getStudent().getEmail(),
                savedEnrollment.getStudent().getBirthDate(),
                savedEnrollment.getCourse().getName(),
                savedEnrollment.getEnrollmentDate()
        );

    }

    public void deleteEnrollment(int id){
        if(!enrollmentRepository.existsById(id)){
            log.error("Failed to delete: Enrollment with id {} not found", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Enrollment with id: " + id + " not found");
        }
        enrollmentRepository.deleteById(id);
    }
}
