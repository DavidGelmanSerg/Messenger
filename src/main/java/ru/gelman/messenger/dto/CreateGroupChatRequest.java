package ru.gelman.messenger.dto;

import java.util.List;

public record CreateGroupChatRequest(String name, String description, List<Long> userIds) {
}
