package com.project.gradebookmanagementsystem.services;

import com.project.gradebookmanagementsystem.dtos.AuthResponseDto;
import com.project.gradebookmanagementsystem.models.User;

import java.util.List;

public interface UserService {

    User createUser(User user, List<String> roleNames);

    AuthResponseDto login(String username, String password);

    User updateUser(Long id, User user);

    List<User> getAllUser();

    User getUserById(Long id);

    void deleteUser(Long id);

    boolean existsByUsername(String username);

    boolean isExists(Long id);
}
