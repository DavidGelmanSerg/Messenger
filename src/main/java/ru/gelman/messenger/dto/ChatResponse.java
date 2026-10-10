package ru.gelman.messenger.dto;

import ru.gelman.messenger.enums.ChatType;

import java.time.LocalDateTime;
import java.util.List;

public record ChatResponse(Long id, String name, String description, ChatType type, LocalDateTime createdAt,
                           List<Long> userIds) {
}
