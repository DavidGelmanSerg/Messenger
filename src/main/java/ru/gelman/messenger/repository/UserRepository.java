package ru.gelman.messenger.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.gelman.messenger.entity.User;

@Repository
public interface UserRepository extends ListCrudRepository<User, Long> {
    boolean existsByEmail(String email);
}
