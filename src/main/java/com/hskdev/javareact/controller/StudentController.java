package com.hskdev.javareact.controller;


import com.hskdev.javareact.model.Student;
import com.hskdev.javareact.service.IStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("/students")
@RequiredArgsConstructor  // will inject any field that is final
public class StudentController {

    private final IStudentService studentService;

    // to get students from the database
    @GetMapping
    public ResponseEntity<List<Student>> getStudents() {
        return new ResponseEntity<>(studentService.getStudents(), HttpStatus.FOUND);
    }

    // to save students
    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);

    }

    // to update student
    @PutMapping("/update/{id}")
    public Student updateStudent(@RequestBody Student student, @PathVariable Long id) {
        return studentService.updateStudent(student, id);
    }

    // delete student
    @DeleteMapping("/delete/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }

    // grt single student
    @GetMapping("/student/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }
}

