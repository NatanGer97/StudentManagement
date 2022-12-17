package com.backend.StudentManagement.Controllers;

import com.backend.StudentManagement.Services.*;
import com.backend.StudentManagement.models.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

import javax.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/students")
public class StudentsController {
    private final Logger logger = LoggerFactory.getLogger(StudentsController.class);

    @Autowired
    private StudentService studentService;


    @Autowired
    private AwsService awsService;


    @GetMapping("")
    public ResponseEntity<?> getAllStudents(@RequestParam(defaultValue = "0") Integer pageNo,
                                            @RequestParam(defaultValue = "10") Integer pageSize,
                                            @RequestParam(defaultValue = "Id") eSortOrder sortOrderField,
                                            @RequestParam(defaultValue = "false") Boolean isDescending) {
        return new ResponseEntity<>(studentService.all(pageNo, pageSize, sortOrderField, isDescending), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getOneStudent(@PathVariable Long id) {
        Optional<Student> student = studentService.findById(id);
        if (student.isPresent()) {
            return new ResponseEntity<>(StudentOut.of(student.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Student not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/highSat")
    public ResponseEntity<?> getStudentWithSatHigherThan(@RequestParam Integer sat) {
        return new ResponseEntity<>(studentService.getStudentWithSatHigherThan(sat), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> insertStudent(@RequestBody StudentIn studentIn) {
        Student student = studentIn.toStudent();
        student = studentService.save(student);
        return new ResponseEntity<>(student, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Long id, @RequestBody StudentIn student) {
        Optional<Student> dbStudent = studentService.findById(id);
        // TODO:  handle not found
        if (dbStudent.isEmpty()) throw new RuntimeException("Student with id: " + id + " not found");
        student.updateStudent(dbStudent.get());
        Student updatedStudent = studentService.save(dbStudent.get());
        return new ResponseEntity<>(StudentOut.of(updatedStudent), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        Optional<Student> dbStudent = studentService.findById(id);
        // TODO:  handle not found
        if (dbStudent.isEmpty()) throw new RuntimeException("Student with id: " + id + " not found");
        studentService.delete(dbStudent.get());

        return new ResponseEntity<>("DELETED", HttpStatus.OK);
    }

    @PutMapping("/{id}/image/upload")
    public ResponseEntity<?> uploadStudentImage(@PathVariable Long id, @RequestParam("image") MultipartFile image) {
        Optional<Student> dbStudent = studentService.findById(id);
        //todo: handle not found
        if (dbStudent.isEmpty()) throw new RuntimeException("Student with id: " + id + " not found");

        String bucketPath = "studentManagement/natan/student-" + id + ".png";

        awsService.putInBucket(image, bucketPath);
        dbStudent.get().setProfilePicture(bucketPath);

        Student updatedStudent = studentService.save(dbStudent.get());
        return new ResponseEntity<>(StudentOut.of(updatedStudent, awsService), HttpStatus.OK);
    }

    @PostMapping("/{studentId}/sendEmail")
    public ResponseEntity<?> sendEmail(@PathVariable Long studentId, @RequestBody(required = true) EmailDetails email) {
        Optional<Student> dbStudent = studentService.findById(studentId);
        if (dbStudent.isEmpty()) throw new RuntimeException("Student with id: " + studentId + " not found");
        logger.info(email.toString());


        email.setRecipient(dbStudent.get().getEmail());

        studentService.sendEmail(email);
        return new ResponseEntity<>("Sending...", HttpStatus.OK);


    }
}
