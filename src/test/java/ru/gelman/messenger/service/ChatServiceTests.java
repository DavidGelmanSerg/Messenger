package ru.gelman.messenger.service;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.gelman.messenger.dto.ChatResponse;
import ru.gelman.messenger.dto.CreateGroupChatRequest;
import ru.gelman.messenger.dto.CreatePersonalChatRequest;
import ru.gelman.messenger.entity.Chat;
import ru.gelman.messenger.entity.User;
import ru.gelman.messenger.enums.ChatType;
import ru.gelman.messenger.exception.ChatCreationException;
import ru.gelman.messenger.mapping.UserMapper;
import ru.gelman.messenger.repository.ChatRepository;
import ru.gelman.messenger.service.chat.ChatService;
import ru.gelman.messenger.service.user.UserInternalService;
import ru.gelman.messenger.service.user.UserService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ChatServiceTests {
    @Autowired
    private ChatService chatService;

    @Autowired
    private UserMapper userMapper;

    @MockitoBean
    private UserInternalService userService;

    @MockitoBean
    private UserService userInternalService;

    @MockitoBean
    private ChatRepository chatRepository;

    @Test
    void shouldCreatePersonalChat() {
        User firstTestUser = User.builder().id(1L).name("User1").email("testEmail1@gmail.com").build();
        User secondTestUser = User.builder().id(2L).name("User2").email("testEmail2@gmail.com").build();
        List<User> personalChatUsers = List.of(firstTestUser, secondTestUser);
        List<Long> personalChatUserIds = personalChatUsers.stream().map(User::getId).toList();
        CreatePersonalChatRequest request = new CreatePersonalChatRequest(firstTestUser.getId(), secondTestUser.getId());
        Chat testPersonalChat = Chat.personal(firstTestUser, secondTestUser);
        Chat createdPersonalChat = Chat.personal(firstTestUser, secondTestUser);
        createdPersonalChat.setId(1L);

        configureUserServiceFindByIdMethod(personalChatUsers);
        configureChatRepositorySaveMethod(testPersonalChat, createdPersonalChat);

        ChatResponse creatingPersonalChatResult = chatService.createPersonalChat(request);

        assertEquals(creatingPersonalChatResult.id(), createdPersonalChat.getId());
        assertEquals(creatingPersonalChatResult.type(), ChatType.PERSONAL);
        assertIterableEquals(personalChatUserIds, creatingPersonalChatResult.userIds());
    }

    private void configureUserServiceFindByIdMethod(List<User> users) {
        users.forEach(user -> Mockito.when(userService.getUserById(user.getId())).thenReturn(user));
    }

    private void configureChatRepositorySaveMethod(Chat creating, Chat created) {
        Mockito.when(chatRepository.save(creating)).thenReturn(created);
    }

    @Test
    void shouldCreateGroupChat() {
        List<User> members = List.of(
                User.builder().id(1L).name("User1").email("testEmail1@gmail.com").build(),
                User.builder().id(2L).name("User2").email("testEmail2@gmail.com").build(),
                User.builder().id(3L).name("User3").email("testEmail3@gmail.com").build()
        );

        List<Long> memberIds = members.stream().map(User::getId).toList();
        Chat testChat = Chat.group("test chat", "test description", members);
        CreateGroupChatRequest request = new CreateGroupChatRequest(testChat.getName(), testChat.getDescription(), memberIds);
        Chat createdGroupChat = Chat.group(testChat.getName(), testChat.getDescription(), testChat.getUsers());
        createdGroupChat.setId(1L);

        configureUserServiceFindByIdMethod(members);
        configureChatRepositorySaveMethod(testChat, createdGroupChat);

        ChatResponse createGroupChatResult = chatService.createGroupChat(request);

        assertEquals(createGroupChatResult.id(), createdGroupChat.getId());
        assertEquals(createGroupChatResult.type(), ChatType.GROUP);
        assertEquals(createGroupChatResult.name(), createdGroupChat.getName());
        assertEquals(createGroupChatResult.description(), createdGroupChat.getDescription());
        assertIterableEquals(memberIds, createGroupChatResult.userIds());
    }

    @Test
    void shouldThrowGroupChatCreationException() {
        List<User> emptyUserList = Collections.emptyList();
        Chat testChat = Chat.group("test chat", "test description", emptyUserList);
        CreateGroupChatRequest request = new CreateGroupChatRequest(testChat.getName(), testChat.getDescription(), Collections.emptyList());

        assertThrows(ChatCreationException.class, () -> chatService.createGroupChat(request));
    }
}
