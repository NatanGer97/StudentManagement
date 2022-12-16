package com.backend.StudentManagement.Services;

import com.backend.StudentManagement.Repos.*;
import com.backend.StudentManagement.models.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class StudentService {

    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    StudentRepository studentRepository;

    public Iterable<Student> all() {
        return studentRepository.findAll();
    }

    public PaginationAndData all(int pageNumber, int pageSize, eSortOrder sortOrderField, Boolean isDescending) {
        PageRequest request = PageRequest.of(pageNumber, pageSize,
                isDescending ? Sort.by(sortOrderField.getValue()).descending() : Sort.by(sortOrderField.getValue()).ascending());
        Page<Student> studentPage = studentRepository.findAll(request);
        logger.info("Total pages: " + studentPage.getTotalPages());
        logger.info("Total elements: " + studentPage.getTotalElements());
        logger.info("Page number: " + studentPage.getNumber());
        Pagination pagination = Pagination.of(studentPage.getNumber(), studentPage.getTotalPages());
        PaginationAndData paginationAndData = PaginationAndData.of(pagination, studentPage.getContent());
        return paginationAndData;

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
