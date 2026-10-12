package ru.gelman.messenger.service.chat;

import com.google.common.collect.Iterables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gelman.messenger.dto.*;
import ru.gelman.messenger.entity.Chat;
import ru.gelman.messenger.entity.Message;
import ru.gelman.messenger.entity.User;
import ru.gelman.messenger.exception.ChatCreationException;
import ru.gelman.messenger.exception.NotFoundException;
import ru.gelman.messenger.mapping.ChatMapper;
import ru.gelman.messenger.mapping.MessageMapper;
import ru.gelman.messenger.repository.ChatRepository;
import ru.gelman.messenger.service.user.UserInternalService;

import java.util.List;

@Service
@Slf4j
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;

    private final UserInternalService userService;
    private final ChatMapper chatMapper;
    private final MessageMapper messageMapper;

    @Autowired
    public ChatServiceImpl(ChatRepository repository, UserInternalService userService, ChatMapper mapper, MessageMapper messageMapper) {
        this.chatRepository = repository;
        this.userService = userService;
        this.chatMapper = mapper;
        this.messageMapper = messageMapper;
    }

    @Override
    public ChatResponse createPersonalChat(CreatePersonalChatRequest request) {
        User initiator = userService.getUserById(request.initiatorUserId());
        User recipient = userService.getUserById(request.recipientUserId());
        Chat personalChat = Chat.personal(initiator, recipient);
        log.debug("Saving new {} to datasource", personalChat);

        return chatMapper.toChatResponse(chatRepository.save(personalChat));
    }

    @Override
    public ChatResponse createGroupChat(CreateGroupChatRequest request) {
        List<User> users = request.userIds().stream().map(userService::getUserById).toList();
        if (users.isEmpty()) {
            throw new ChatCreationException("Can not create group chat with empty user list");
        }
        Chat groupChat = Chat.group(request.name(), request.description(), users);
        log.debug("Saving new {} to datasource", groupChat);
        return chatMapper.toChatResponse(chatRepository.save(groupChat));
    }

    @Override
    public List<ChatResponse> findUserChatList(long id) {
        List<Chat> userChatList = userService.getUserById(id).getChats();
        log.debug("Found users chats: {}", userChatList);
        return userChatList.stream().map(chatMapper::toChatResponse).toList();
    }

    @Override
    public MessageDto createMessageInChat(SimpleTextMessageRequest request) {
        User sender = userService.getUserById(request.senderId());
        Chat chat = chatRepository
                .findById(request.chatId())
                .orElseThrow(() -> new NotFoundException("Can not find chat by id %d".formatted(request.chatId())));

        chat.getMessages().add(Message.simpleTextMessage(sender, request.content()));
        log.debug("Saving chat {} with updated message list {}", chat, chat.getMessages());
        List<Message> updateMessageList = chatRepository.save(chat).getMessages();
        log.debug("Updated chat message list {}", updateMessageList);
        return messageMapper.toMessageDto(Iterables.getLast(updateMessageList));
    }
}
