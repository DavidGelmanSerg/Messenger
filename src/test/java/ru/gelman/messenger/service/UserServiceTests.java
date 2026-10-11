package ru.gelman.messenger.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.gelman.messenger.dto.CreateUserDto;
import ru.gelman.messenger.dto.UserDto;
import ru.gelman.messenger.entity.User;
import ru.gelman.messenger.exception.AlreadyExistsException;
import ru.gelman.messenger.exception.NotFoundException;
import ru.gelman.messenger.repository.UserRepository;
import ru.gelman.messenger.service.user.UserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UserServiceTests {

    private static User testUser;

    private static User created;
    private static CreateUserDto userInfo;
    @Autowired
    private UserService userService;
    @MockitoBean
    private UserRepository repository;

    @BeforeAll
    static void prepareUser() {
        testUser = User.builder().name("David").email("testemail@gmail.com").build();
        userInfo = new CreateUserDto(testUser.getName(), testUser.getEmail());
        created = User.builder().id(1L).name(testUser.getName()).email(testUser.getEmail()).build();
    }

    @Test
    void shouldCreateUserTest() {
        Mockito.when(repository.save(testUser)).thenReturn(created);
        UserDto newUser = userService.create(userInfo);

        assertEquals(newUser.id(), created.getId());
        assertEquals(newUser.name(), testUser.getName());
        assertEquals(newUser.email(), testUser.getEmail());
    }

    @Test
    void shouldThrowAlreadyExistsExceptionTest() {
        Mockito.when(repository.existsByEmail(testUser.getEmail())).thenReturn(true);
        assertThrows(AlreadyExistsException.class, () -> userService.create(userInfo));
    }

    @Test
    void shouldReturnUser() {
        Mockito.when(repository.findById(created.getId())).thenReturn(Optional.of(created));
        UserDto user = userService.findById(created.getId());

        assertEquals(user.id(), created.getId());
    }

    @Test
    void shouldThrowNotFoundException() {
        Mockito.when(repository.findById(created.getId())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> userService.findById(created.getId()));
    }
}
