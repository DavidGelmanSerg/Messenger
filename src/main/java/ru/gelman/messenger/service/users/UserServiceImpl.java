package ru.gelman.messenger.service.users;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gelman.messenger.entity.User;
import ru.gelman.messenger.exception.AlreadyExistsException;
import ru.gelman.messenger.repository.UserRepository;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User create(User user) {
        if (repository.existsByEmail(user.getEmail())) {
            throw new AlreadyExistsException("User with email %s already exists".formatted(user.getEmail()));
        }
        log.debug("Saving {} to data source", user);
        return repository.save(user);
    }
}
