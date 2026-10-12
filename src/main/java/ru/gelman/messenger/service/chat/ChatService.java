package ru.gelman.messenger.service.chat;

import ru.gelman.messenger.dto.*;

import java.util.List;

public interface ChatService {
    ChatResponse createPersonalChat(CreatePersonalChatRequest request);

    ChatResponse createGroupChat(CreateGroupChatRequest request);

    List<ChatResponse> findUserChatList(long id);

    MessageDto createMessageInChat(SimpleTextMessageRequest request);
}
