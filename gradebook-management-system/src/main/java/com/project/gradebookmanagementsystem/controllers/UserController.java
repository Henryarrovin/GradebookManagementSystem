package com.project.gradebookmanagementsystem.controllers;

import com.project.gradebookmanagementsystem.dtos.AuthResponseDto;
import com.project.gradebookmanagementsystem.dtos.LoginDto;
import com.project.gradebookmanagementsystem.dtos.UserDto;
import com.project.gradebookmanagementsystem.mappers.Mapper;
import com.project.gradebookmanagementsystem.models.User;
import com.project.gradebookmanagementsystem.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
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
    public ResponseEntity<AuthResponseDto> loginUser(@RequestBody LoginDto loginDto) {
        if (loginDto.getUsername() == null || loginDto.getPassword() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        AuthResponseDto token = userService.login(loginDto.getUsername(), loginDto.getPassword());

        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("create-user")
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto) {

        if (userDto.getFirstName() == null ||
            userDto.getLastName() == null ||
            userDto.getUsername() == null ||
            userDto.getPassword() == null) {
            return new ResponseEntity<>("All required fields must be provided", HttpStatus.BAD_REQUEST);
        }
        if (userService.existsByUsername(userDto.getUsername())) {
            return new ResponseEntity<>("Username already exists",HttpStatus.CONFLICT);
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
    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
