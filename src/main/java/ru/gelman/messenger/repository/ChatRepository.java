package ru.gelman.messenger.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.gelman.messenger.entity.Chat;

@Repository
public interface ChatRepository extends CrudRepository<Chat, Long> {
}
