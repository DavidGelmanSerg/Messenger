package ru.gelman.messenger.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.gelman.messenger.dto.*;
import ru.gelman.messenger.service.chat.ChatService;

import java.util.List;

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

    @GetMapping("/user/{id}")
    public List<ChatResponse> getUserChatList(@PathVariable long id) {
        log.info("Searching chats for user {}", id);
        List<ChatResponse> userChatList = chatService.findUserChatList(id);
        log.info("Found user chats: {}", userChatList);
        return userChatList;
    }

    @PostMapping("/{id}/messages")
    public MessageDto sendMessage(@PathVariable long id, @RequestBody SimpleTextMessageRequest request) {
        log.info("Creating message {}", request);
        MessageDto created = chatService.createMessageInChat(request);
        log.info("Created {}", created);
        return created;
    }
}
