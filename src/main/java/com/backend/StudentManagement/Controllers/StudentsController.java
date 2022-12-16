package com.backend.StudentManagement.Controllers;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.*;
@RestController
@RequestMapping("/api/students")
public class StudentsController {

    @GetMapping("")
    public ResponseEntity<?> hello()
    {
        return new ResponseEntity<>("Hello World", HttpStatus.OK);
    }
}
