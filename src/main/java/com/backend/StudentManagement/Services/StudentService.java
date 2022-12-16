package com.backend.StudentManagement.Services;

import com.backend.StudentManagement.Repos.*;
import com.backend.StudentManagement.models.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;

    public Iterable<Student> all() {
        return studentRepository.findAll();
    }

    public Iterable<Student> all(int pageNumber, int pageSize, eSortOrder sortOrderField, Boolean isDescending) {
        PageRequest request = PageRequest.of(pageNumber, pageSize,
                isDescending ? Sort.by(sortOrderField.getValue()).descending() : Sort.by(sortOrderField.getValue()).ascending());
        Page<Student> studentPage = studentRepository.findAll(request);
        return studentPage.getContent();
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

    public List<Student> getStudentWithSatHigherThan(Integer sat) {
        return studentRepository.findAllBySatScoreGreaterThan(sat);
    }
}
