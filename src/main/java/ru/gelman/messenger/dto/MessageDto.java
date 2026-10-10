package ru.gelman.messenger.dto;

import java.time.LocalDateTime;
import java.util.List;

public record MessageDto(Long id, LocalDateTime createdAt, UserDto sender, List<TextContentDto> textContents) {
}
