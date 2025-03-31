package com.vjezba.Sustav_upisa_studenata.Course;

import com.vjezba.Sustav_upisa_studenata.Student.StudentResponseDTO;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private static final Logger log = LoggerFactory.getLogger(CourseController.class);
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<CourseResponseDTO> findAllCourses(){
        return courseService.findAllCourses();
    }

    @GetMapping("/{id}")
    public CourseResponseDTO findCourseById(@PathVariable int id){
        return courseService.findCourseById(id);
    }

    @GetMapping("/{courseId}/students")
    public List<StudentResponseDTO> getStudentsForCourse(@PathVariable int courseId){
        return courseService.getStudentsForCourse(courseId);
    }


    @PostMapping
    public CourseResponseDTO createCourse(@RequestBody @Valid CourseDTO courseDTO) {
        return courseService.saveCourse(courseDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable int id){
        courseService.deleteCourse(id);
    }
}
