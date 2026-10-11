package ru.gelman.messenger.service.chat;

import ru.gelman.messenger.dto.ChatResponse;
import ru.gelman.messenger.dto.CreateGroupChatRequest;
import ru.gelman.messenger.dto.CreatePersonalChatRequest;

import java.util.List;

public interface ChatService {
    ChatResponse createPersonalChat(CreatePersonalChatRequest request);

    ChatResponse createGroupChat(CreateGroupChatRequest request);

    List<ChatResponse> findUserChatList(long id);
}
