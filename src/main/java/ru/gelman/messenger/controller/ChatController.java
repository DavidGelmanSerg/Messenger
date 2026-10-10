package ru.gelman.messenger.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gelman.messenger.dto.ChatResponse;
import ru.gelman.messenger.dto.CreateGroupChatRequest;
import ru.gelman.messenger.dto.CreatePersonalChatRequest;
import ru.gelman.messenger.service.chat.ChatService;

@RestController
@RequestMapping("/chats")
@Slf4j
public class ChatController {
    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/personal")
    public ChatResponse createPersonalChat(@RequestBody CreatePersonalChatRequest request) {
        log.info("Creating personal chat between users with id {} and {}", request.initiatorUserId(), request.recipientUserId());
        ChatResponse created = chatService.createPersonalChat(request);
        log.info("Successfully created personal {}", created);
        return created;
    }

    @PostMapping("/group")
    public ChatResponse createGroupChat(@RequestBody CreateGroupChatRequest request) {
        log.info("Creating group chat {}", request);
        ChatResponse created = chatService.createGroupChat(request);
        log.info("Successfully created personal {}", created);
        return created;
    }

}
