package com.vjezba.Sustav_upisa_studenata.Enrollment;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {
    private static final Logger log = LoggerFactory.getLogger(EnrollmentController.class);
    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    public List<EnrollmentResponseDTO> findAllEnrollments(){
        return enrollmentService.findAllEnrollments();
    }

    @GetMapping("/{id}")
    public EnrollmentResponseDTO findEnrollmentById(@PathVariable int id){
        return enrollmentService.findEnrollmentById(id);
    }

    @GetMapping("/student/{studentId}")
    public List<EnrollmentResponseDTO> findEnrollmentByStudentId(@PathVariable int studentId){
        return enrollmentService.findEnrollmentByStudentId(studentId);
    }

    @PostMapping
    public EnrollmentResponseDTO createEnrollment(@Valid @RequestBody EnrollmentDTO enrollmentDTO){
        return enrollmentService.saveEnrollment(enrollmentDTO);
    }

    @DeleteMapping("{id}")
    public void deleteEnrollment(@PathVariable int id){
        enrollmentService.deleteEnrollment(id);
    }
}
