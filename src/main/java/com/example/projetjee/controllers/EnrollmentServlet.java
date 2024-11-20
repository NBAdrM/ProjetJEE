package com.example.projetjee.controllers;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class EnrollmentServlet {
    @RestController
    @RequestMapping("/enrollments")
    public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @PostMapping("/enroll")
    public ResponseEntity<Enrollment> enrollStudent(@RequestBody Enrollment enrollment) {
        Enrollment newEnrollment = enrollmentService.enrollStudent(enrollment);
        return ResponseEntity.status(HttpStatus.CREATED).body(newEnrollment);
    }

    @GetMapping("/student/{studentId}")
    public List<Course> getCoursesByStudentId(@PathVariable Long studentId) {
        return enrollmentService.getCoursesByStudentId(studentId);
    }
}

}
