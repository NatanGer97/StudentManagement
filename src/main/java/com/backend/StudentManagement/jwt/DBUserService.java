package com.backend.StudentManagement.jwt;


import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class DBUserService {


    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(DBUserService.class);

    @Autowired
    private DBUserRepository repository;

    public Optional<DBUser> findUserName(String userName) {
            return repository.findByName(userName);
    }

    public void save(DBUser user) {
        repository.save(user);
    }
}
