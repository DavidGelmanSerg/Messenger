package ru.gelman.messenger.service.user;

import ru.gelman.messenger.entity.User;

public interface UserService {
    User create(User user);
    User findById(Long id);
}
