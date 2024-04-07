package com.project.gradebookmanagementsystem.services.impl;

import com.project.gradebookmanagementsystem.models.User;
import com.project.gradebookmanagementsystem.repositories.UserRepository;
import com.project.gradebookmanagementsystem.security.JwtTokenProvider;
import com.project.gradebookmanagementsystem.services.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private JwtTokenProvider jwtTokenProvider;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(User user) {
        // Encode password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
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
    public String login(String username, String password) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByUsername(username));
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return jwtTokenProvider.generateToken((UserDetails) user);
            }
        }
        return null;
    }

    @Override
    public boolean isExists(Long id) {
        return userRepository.existsById(id);
    }
}
