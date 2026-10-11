package ru.gelman.messenger.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.gelman.messenger.dto.CreateUserDto;
import ru.gelman.messenger.dto.UserDto;
import ru.gelman.messenger.service.user.UserService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public UserDto createUser(@RequestBody CreateUserDto userDto) {
        log.info("Creating user {} - {}", userDto.name(), userDto.email());
        UserDto created = userService.create(userDto);
        log.info("Successfully created {}", created);
        return created;
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable long id) {
        log.info("Searching user by id {}", id);
        UserDto user = userService.findById(id);
        log.info("Found user {}", user);
        return user;
    }

    @GetMapping("/")
    public List<UserDto> getUserList(@RequestBody List<Long> ids) {
        log.info("Searching user by ids {}", ids);
        List<UserDto> users = userService.findAllById(ids);
        log.info("Found users {}", users);
        return users;
    }
}
