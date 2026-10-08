package ru.gelman.messenger.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.gelman.messenger.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    boolean existsByEmail(String email);
}
