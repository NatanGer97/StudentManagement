package com.backend.StudentManagement.jwt;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private DBUserService userService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        Optional<DBUser> dbUser = userService.findUserName(userName);
        if (dbUser.isPresent()) {
            return new User(dbUser.get().getName(), dbUser.get().getPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found : " + userName);
        }
    }
}
