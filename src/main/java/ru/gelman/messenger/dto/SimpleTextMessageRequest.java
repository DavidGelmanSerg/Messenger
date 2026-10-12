package ru.gelman.messenger.dto;

public record SimpleTextMessageRequest(long senderId, long chatId, String content) {
}
