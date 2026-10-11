package ru.gelman.messenger.service.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gelman.messenger.dto.CreateUserDto;
import ru.gelman.messenger.dto.UserDto;
import ru.gelman.messenger.entity.User;
import ru.gelman.messenger.exception.AlreadyExistsException;
import ru.gelman.messenger.exception.NotFoundException;
import ru.gelman.messenger.mapping.UserMapper;
import ru.gelman.messenger.repository.UserRepository;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService, UserInternalService {

    private final UserRepository repository;
    private final UserMapper mapper;

    @Autowired
    public UserServiceImpl(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public UserDto create(CreateUserDto userInfo) {
        if (repository.existsByEmail(userInfo.email())) {
            throw new AlreadyExistsException("User with email %s already exists".formatted(userInfo.email()));
        }
        User user = mapper.convertToEntity(userInfo);
        log.debug("Saving {} to data source", user);
        return mapper.convertToDto(repository.save(user));
    }

    @Override
    public UserDto findById(long id) {
        return mapper.convertToDto(getUserById(id));
    }

    @Override
    public List<UserDto> findAllById(List<Long> ids) {
        return mapper.convertToDto(repository.findAllById(ids));
    }

    @Override
    public User getUserById(long id) {
        return getById(id);
    }

    private User getById(long id) {
        User user = repository.findById(id).orElseThrow(() -> new NotFoundException("User with id %s not found".formatted(id)));
        log.debug("Found {}", user);
        return user;
    }
}
