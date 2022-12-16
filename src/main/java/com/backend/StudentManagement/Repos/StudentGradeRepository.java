package com.backend.StudentManagement.Repos;

import com.backend.StudentManagement.models.*;
import org.springframework.data.domain.*;
import org.springframework.data.repository.*;

import java.util.*;

public interface StudentGradeRepository extends CrudRepository<StudentGrade, Long> {
    List<StudentGrade> findByStudent_Id(Long id);



}
