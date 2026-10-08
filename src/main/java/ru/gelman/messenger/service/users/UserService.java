package ru.gelman.messenger.service.users;

import ru.gelman.messenger.dto.CreateUserDto;
import ru.gelman.messenger.entity.User;

public interface UserService {
    User create(User user);
}
