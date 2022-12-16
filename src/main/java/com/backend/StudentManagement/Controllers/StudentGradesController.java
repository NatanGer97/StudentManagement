package com.backend.StudentManagement.Controllers;

import com.backend.StudentManagement.Services.*;
import com.backend.StudentManagement.models.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/students")
public class StudentGradesController {

    @Autowired
    private StudentGradeService studentGradeService;
    @Autowired
    private StudentService studentService;

    /**
     * returns all grades for a student
     * @param studentId the id of the student
     * @return a list of grades
     */
    @GetMapping("/{studentId}/grades")
    public ResponseEntity<?> getStudentGrades(@PathVariable Long studentId)
    {
        Optional<Student> student = studentService.findById(studentId);
        if (student.isPresent())
        {
            return new ResponseEntity<>(studentGradeService.findByStudent_Id(student.get().getId()), HttpStatus.OK);
        }
        else
        {
            // TODO: 16/12/2022  throw exception
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{studentId}/grades")
    public ResponseEntity<?> insertStudentGrade(@PathVariable Long studentId, @RequestBody GradeIn studentGradeIn) {
        Optional<Student> student = studentService.findById(studentId);
        if (student.isPresent()) {
            StudentGrade studentGrade = studentGradeIn.toGrade(student.get());
            studentGrade = studentGradeService.save(studentGrade);

            return new ResponseEntity<>(studentGrade, HttpStatus.CREATED);
        } else {
            // TODO: throw exception instead
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{studentId}/grades/{id}")
    public ResponseEntity<?> updateStudentGrade(@PathVariable Long studentId,
                                                @PathVariable Long id,
                                                @RequestBody GradeIn studentGradeIn) {
        Optional<Student> student = studentService.findById(studentId);
        if (student.isPresent()) {
            Optional<StudentGrade> studentGrade = studentGradeService.findById(id);
            if (studentGrade.isPresent()) {
                studentGradeIn.updateStudentGrade(studentGrade.get());
                studentGrade = Optional.of(studentGradeService.save(studentGrade.get()));
                return new ResponseEntity<>(studentGrade, HttpStatus.OK);
            } else {
                // TODO: throw exception instead
                return new ResponseEntity<>("Student grade with id: " + id + " not found",
                        HttpStatus.NOT_FOUND);
            }
        } else {
            // TODO: throw exception instead
            return new ResponseEntity<>("Student with id: " + studentId + " not found",
                    HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Delete a student grade
     * @param studentId the student id
     * @param id the student grade id
     * @return
     */
    @DeleteMapping("/{studentId}/grades/{id}")
    public ResponseEntity<?> deleteStudentGrade(@PathVariable Long studentId, @PathVariable Long id) {
        Optional<Student> student = studentService.findById(studentId);

        if (student.isPresent()) {
            Optional<StudentGrade> studentGrade = studentGradeService.findById(id);
            if (studentGrade.isPresent()) {
                studentGradeService.delete(studentGrade.get());
                return new ResponseEntity<>("DELETED",HttpStatus.OK);

            } else {
                return new ResponseEntity<>("Student grade with id: " + id + " not found",
                        HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("Student with id: " + studentId + " not found",
                    HttpStatus.NOT_FOUND);
        }
    }
}
