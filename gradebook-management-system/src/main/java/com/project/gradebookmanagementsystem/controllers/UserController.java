package com.project.gradebookmanagementsystem.controllers;

import com.project.gradebookmanagementsystem.dtos.LoginDto;
import com.project.gradebookmanagementsystem.dtos.UserDto;
import com.project.gradebookmanagementsystem.mappers.Mapper;
import com.project.gradebookmanagementsystem.models.User;
import com.project.gradebookmanagementsystem.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private Mapper<User, UserDto> userMapper;

    public UserController(UserService userService, Mapper<User, UserDto> userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody LoginDto loginDto) {
        return userService.login(loginDto.getUsername(), loginDto.getPassword());
    }

    @PostMapping("create-user")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {

        if (userDto.getFirstName() == null ||
            userDto.getLastName() == null ||
            userDto.getUsername() == null ||
            userDto.getPassword() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User user = userMapper.mapFrom(userDto);
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(userMapper.mapTo(createdUser), HttpStatus.CREATED);
    }

    @PutMapping("update-user/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {

        if (!userService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User user = userMapper.mapFrom(userDto);
        User updatedUser = userService.updateUser(id, user);
        return new ResponseEntity<>(userMapper.mapTo(updatedUser), HttpStatus.OK);
    }

    @GetMapping("get-all-user")
    public ResponseEntity<Iterable<UserDto>> getAllUsers() {
        Iterable<User> users = userService.getAllUser();
        List<UserDto> userDto = new ArrayList<>();
        for (User user : users) {
            userDto.add(userMapper.mapTo(user));
        }
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("get-user/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {

        User user = userService.getUserById(id);

        if (!userService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userMapper.mapTo(user), HttpStatus.OK);
    }

    @DeleteMapping("delete-user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
