package com.vjezba.Sustav_upisa_studenata.Student;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private static final Logger log = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;


    public StudentService(StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    public List<StudentResponseDTO> findAllStudents(){
        List<StudentResponseDTO> students  = studentRepository.findAll()
                .stream()
                .map(studentMapper::toStudentResponseDTO)
                .collect(Collectors.toList());

        if (students.isEmpty()) {
            log.warn("No students found in the database.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No students found in the database.");
        }

        return students;
    }

    public StudentResponseDTO saveStudent(StudentDTO studentDTO) {
        if(studentRepository.existsByEmail(studentDTO.email())){
            log.error("Attempt to register with existing email: {}", studentDTO.email());
            throw new IllegalArgumentException("Email is already in use.");
        }
        Student student = studentMapper.toStudent(studentDTO);
        Student savedStudent = studentRepository.save(student);
        return studentMapper.toStudentResponseDTO(savedStudent);
    }

    public void deleteStudent(int id){
        if(!studentRepository.existsById(id)){
            log.error("Failed to delete: Student with id {} not found", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student with id: " + id + " not found");
        }
        studentRepository.deleteById(id);
    }

    public StudentResponseDTO findStudentById(int id) {
        return studentRepository.findById(id)
                .map(studentMapper::toStudentResponseDTO)
                .orElseThrow(() -> {
                    log.error("Student with id {} not found", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Student with id: " + id + " not found");
                });
    }
}
