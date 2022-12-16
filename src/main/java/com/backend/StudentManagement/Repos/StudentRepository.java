package com.backend.StudentManagement.Repos;

import com.backend.StudentManagement.models.*;
import org.springframework.data.repository.*;

public interface StudentRepository  extends CrudRepository<Student, Long> {
}
