package ru.gelman.messenger.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.gelman.messenger.entity.User;
import ru.gelman.messenger.exception.AlreadyExistsException;
import ru.gelman.messenger.repository.UserRepository;
import ru.gelman.messenger.service.users.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UserServiceTests {

    private static User testUser;
    @Autowired
    private UserService userService;
    @MockitoBean
    private UserRepository repository;

    @BeforeAll
    static void prepareUser() {
        testUser = new User();
        testUser.setName("David");
        testUser.setEmail("testemail@gmail.com");
    }

    @Test
    void shouldCreateUserTest() {
        User created = new User();
        created.setId(1L);
        created.setName(testUser.getName());
        created.setEmail(testUser.getEmail());
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
}
