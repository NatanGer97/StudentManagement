package com.backend.StudentManagement.Controllers;

import com.backend.StudentManagement.Services.*;
import com.backend.StudentManagement.models.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/students")
public class StudentsController {


    @Autowired
    private StudentService studentService;


    /*@GetMapping("")
    public ResponseEntity<?> getAllStudents()
    {
        return new ResponseEntity<>(studentService.all(), HttpStatus.OK);
    }*/

    @GetMapping("")
    public ResponseEntity<?> getAllStudents(@RequestParam(defaultValue = "0") Integer pageNo,
                                            @RequestParam(defaultValue = "10") Integer pageSize,
                                            @RequestParam(defaultValue = "Id") eSortOrder sortOrderField,
                                            @RequestParam(defaultValue = "false") Boolean isDescending)
    {
        return new ResponseEntity<>(studentService.all(pageNo, pageSize,sortOrderField,isDescending), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getOneStudent(@PathVariable Long id)
    {
        return new ResponseEntity<>(studentService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/highSat")
    public ResponseEntity<?> getStudentWithSatHigherThan(@RequestParam Integer sat)
    {
        return new ResponseEntity<>(studentService.getStudentWithSatHigherThan(sat), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> insertStudent(@RequestBody StudentIn studentIn)
    {
        Student student = studentIn.toStudent();
        student = studentService.save(student);
        return new ResponseEntity<>(student, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Long id, @RequestBody StudentIn student)
    {
        Optional<Student> dbStudent = studentService.findById(id);
        // TODO:  handle not found
        if (dbStudent.isEmpty()) throw new RuntimeException("Student with id: " + id + " not found");
        student.updateStudent(dbStudent.get());
        Student updatedStudent = studentService.save(dbStudent.get());
        return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id)
    {
        Optional<Student> dbStudent = studentService.findById(id);
        // TODO:  handle not found
        if (dbStudent.isEmpty()) throw new RuntimeException("Student with id: " + id + " not found");
        studentService.delete(dbStudent.get());

        return new ResponseEntity<>("DELETED", HttpStatus.OK);
    }
}
