package com.backend.StudentManagement.Services;

import com.backend.StudentManagement.Repos.*;
import com.backend.StudentManagement.models.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;

    public Iterable<Student> all() {
        return studentRepository.findAll();
    }

    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }


    public Student save(Student student) {
        return studentRepository.save(student);
    }

    public void delete(Student student) {
        studentRepository.delete(student);
    }
}
