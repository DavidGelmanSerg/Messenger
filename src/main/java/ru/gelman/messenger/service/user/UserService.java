package ru.gelman.messenger.service.user;

import ru.gelman.messenger.dto.CreateUserDto;
import ru.gelman.messenger.dto.UserDto;

public interface UserService {
    UserDto create(CreateUserDto userInfo);

    UserDto findById(long id);
}
