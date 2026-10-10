package ru.gelman.messenger.service.chat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gelman.messenger.dto.ChatResponse;
import ru.gelman.messenger.dto.CreateGroupChatRequest;
import ru.gelman.messenger.dto.CreatePersonalChatRequest;
import ru.gelman.messenger.entity.Chat;
import ru.gelman.messenger.entity.User;
import ru.gelman.messenger.exception.ChatCreationException;
import ru.gelman.messenger.mapping.ChatMapper;
import ru.gelman.messenger.repository.ChatRepository;
import ru.gelman.messenger.service.user.UserService;

import java.util.List;

@Service
@Slf4j
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;

    private final UserService userService;
    private final ChatMapper mapper;

    @Autowired
    public ChatServiceImpl(ChatRepository repository, UserService userService, ChatMapper mapper) {
        this.chatRepository = repository;
        this.userService = userService;
        this.mapper = mapper;
    }

    @Override
    public ChatResponse createPersonalChat(CreatePersonalChatRequest request) {
        User initiator = userService.findById(request.initiatorUserId());
        User recipient = userService.findById(request.recipientUserId());
        Chat personalChat = Chat.personal(initiator, recipient);
        log.debug("Saving new {} to datasource", personalChat);

        return mapper.toChatResponse(chatRepository.save(personalChat));
    }

    @Override
    public ChatResponse createGroupChat(CreateGroupChatRequest request) {
        List<User> users = request.userIds().stream().map(userService::findById).toList();
        if (users.isEmpty()) {
            throw new ChatCreationException("Can not create group chat with empty user list");
        }
        Chat groupChat = Chat.group(request.name(), request.description(), users);
        log.debug("Saving new {} to datasource", groupChat);
        return mapper.toChatResponse(chatRepository.save(groupChat));
    }
}
