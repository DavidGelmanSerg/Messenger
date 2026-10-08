package ru.gelman.messenger.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gelman.messenger.dto.CreateUserDto;
import ru.gelman.messenger.dto.UserDto;
import ru.gelman.messenger.entity.User;
import ru.gelman.messenger.mapping.UserMapper;
import ru.gelman.messenger.service.users.UserService;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapper mapper;

    @Autowired
    public UserController(UserService userService, UserMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @PostMapping("/")
    public UserDto createUser(@RequestBody CreateUserDto userDto) {
        log.info("Creating user {} - {}", userDto.name(), userDto.email());
        User created = userService.create(mapper.convertToEntity(userDto));
        log.info("Successfully created {}", created);
        return mapper.convertToDto(created);
    }
}
