package com.project.gradebookmanagementsystem.security;

import com.project.gradebookmanagementsystem.models.Student;
import com.project.gradebookmanagementsystem.models.Teacher;
import com.project.gradebookmanagementsystem.models.User;
import com.project.gradebookmanagementsystem.repositories.StudentRepository;
import com.project.gradebookmanagementsystem.repositories.TeacherRepository;
import com.project.gradebookmanagementsystem.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;

public class JwtUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
        }
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
