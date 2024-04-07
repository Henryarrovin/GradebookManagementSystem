package com.project.gradebookmanagementsystem.services;

import com.project.gradebookmanagementsystem.models.User;

import java.util.List;

public interface UserService {

    User createUser(User user);
    User updateUser(Long id, User user);

    List<User> getAllUser();

    User getUserById(Long id);

    void deleteUser(Long id);

    String login(String username, String password);

    boolean isExists(Long id);

}
