package ru.gelman.messenger.dto;

public record CreatePersonalChatRequest(Long initiatorUserId, Long recipientUserId) {
}
