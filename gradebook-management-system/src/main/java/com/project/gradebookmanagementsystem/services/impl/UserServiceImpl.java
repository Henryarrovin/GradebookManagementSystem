package com.project.gradebookmanagementsystem.services.impl;

import com.project.gradebookmanagementsystem.dtos.AuthResponseDto;
import com.project.gradebookmanagementsystem.models.User;
import com.project.gradebookmanagementsystem.repositories.RoleRepository;
import com.project.gradebookmanagementsystem.repositories.UserRepository;
import com.project.gradebookmanagementsystem.security.JwtTokenProvider;
import com.project.gradebookmanagementsystem.services.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private JwtTokenProvider jwtTokenProvider;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public AuthResponseDto login(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        return new AuthResponseDto(token);
    }

    @Override
    public User updateUser(Long id, User user) {
        user.setId(id);
        return userRepository.findById(id)
                .map(existingUser -> {
                    Optional.ofNullable(user.getFirstName()).ifPresent(existingUser::setFirstName);
                    Optional.ofNullable(user.getLastName()).ifPresent(existingUser::setLastName);
                    Optional.ofNullable(user.getEmail()).ifPresent(existingUser::setEmail);
                    Optional.ofNullable(user.getDateOfBirth()).ifPresent(existingUser::setDateOfBirth);
                    return userRepository.save(existingUser);
                }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean isExists(Long id) {
        return userRepository.existsById(id);
    }

}
