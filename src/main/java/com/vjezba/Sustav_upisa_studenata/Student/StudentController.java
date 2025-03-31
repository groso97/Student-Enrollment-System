package com.vjezba.Sustav_upisa_studenata.Student;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    private static final Logger log = LoggerFactory.getLogger(StudentController.class);
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<StudentResponseDTO> findAllStudents(){
        return studentService.findAllStudents();
    }

    @GetMapping("/{id}")
    public StudentResponseDTO findStudentById(@PathVariable int id){
        return studentService.findStudentById(id);
    }

    @PostMapping
    public StudentResponseDTO createStudent(@RequestBody @Valid StudentDTO studentDTO){
        return studentService.saveStudent(studentDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable int id){
        studentService.deleteStudent(id);
    }
}
