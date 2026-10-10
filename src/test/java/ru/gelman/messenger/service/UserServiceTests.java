package ru.gelman.messenger.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
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
    @Autowired
    private UserService userService;
    @MockitoBean
    private UserRepository repository;

    @BeforeAll
    static void prepareUser() {
        testUser = User.builder().name("David").email("testemail@gmail.com").build();
        created = User.builder().id(1L).name(testUser.getName()).email(testUser.getEmail()).build();
    }

    @Test
    void shouldCreateUserTest() {
        Mockito.when(repository.save(testUser)).thenReturn(created);
        User newUser = userService.create(testUser);

        assertEquals(newUser.getId(), created.getId());
        assertEquals(newUser.getName(), testUser.getName());
        assertEquals(newUser.getEmail(), testUser.getEmail());
    }

    @Test
    void shouldThrowAlreadyExistsExceptionTest() {
        Mockito.when(repository.existsByEmail(testUser.getEmail())).thenReturn(true);
        assertThrows(AlreadyExistsException.class, () -> userService.create(testUser));
    }

    @Test
    void shouldReturnUser() {
        Mockito.when(repository.findById(created.getId())).thenReturn(Optional.of(created));
        User user = userService.findById(created.getId());

        assertEquals(user.getId(), created.getId());
    }

    @Test
    void shouldThrowNotFoundException() {
        Mockito.when(repository.findById(created.getId())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> userService.findById(created.getId()));
    }
}
