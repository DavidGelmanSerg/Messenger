package ru.gelman.messenger.service.user;

import ru.gelman.messenger.entity.User;

public interface UserInternalService {
    User getUserById(long id);
}
