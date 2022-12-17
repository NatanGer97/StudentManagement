package com.backend.StudentManagement.jwt;

import org.springframework.data.repository.*;

import java.util.*;

public interface DBUserRepository extends CrudRepository<DBUser,Long> {
    Optional<DBUser> findByName(String name);
}
