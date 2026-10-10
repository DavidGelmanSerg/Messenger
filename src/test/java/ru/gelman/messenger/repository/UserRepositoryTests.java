package ru.gelman.messenger.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import ru.gelman.messenger.entity.User;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class UserRepositoryTests {

    private static User user;
    private final UserRepository userRepository;

    @Autowired
    public UserRepositoryTests(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @BeforeAll
    static void prepareUser() {
        user = User.builder().name("David").email("email@emai.com").build();
    }

    @Test
    void shouldExistsUserByEmailTest() {
        userRepository.save(user);
        assertTrue(userRepository.existsByEmail(user.getEmail()));
    }
}
