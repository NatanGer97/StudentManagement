package com.backend.StudentManagement.Services;

import com.backend.StudentManagement.Repos.*;
import com.backend.StudentManagement.models.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

@Service
public class StudentService {

    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    AwsService awsService;

    public Iterable<Student> all() {
        return studentRepository.findAll();
    }

    public PaginationAndData all(int pageNumber, int pageSize, eSortOrder sortOrderField, Boolean isDescending) {
        PageRequest request = PageRequest.of(pageNumber, pageSize,
                isDescending ? Sort.by(sortOrderField.getValue()).descending() : Sort.by(sortOrderField.getValue()).ascending());
        Page<Student> studentPage = studentRepository.findAll(request);
        List<StudentOut> studentOutList = studentPage.getContent().stream()
                .map(student -> StudentOut.of(student, awsService)).collect(Collectors.toList());

        Pagination pagination = Pagination.of(studentPage.getNumber(), studentPage.getTotalPages());

        return PaginationAndData.of(pagination, studentOutList);

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
