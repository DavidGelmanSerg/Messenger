package ru.gelman.messenger.api;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.gelman.messenger.dto.ChatResponse;
import ru.gelman.messenger.dto.CreateGroupChatRequest;
import ru.gelman.messenger.dto.CreatePersonalChatRequest;
import ru.gelman.messenger.entity.Chat;
import ru.gelman.messenger.entity.User;
import ru.gelman.messenger.mapping.ChatMapper;
import ru.gelman.messenger.service.chat.ChatService;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ChatControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ChatMapper mapper;
    @MockitoBean
    private ChatService chatService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void postCreatePersonalChatTest() throws Exception {
        User firstTestUser = User.builder().id(1L).name("User1").email("testEmail1@gmail.com").build();
        User secondTestUser = User.builder().id(2L).name("User2").email("testEmail2@gmail.com").build();
        Chat testCreatedChat = Chat.personal(firstTestUser, secondTestUser);
        testCreatedChat.setId(1L);
        CreatePersonalChatRequest request = new CreatePersonalChatRequest(firstTestUser.getId(), secondTestUser.getId());
        ChatResponse response = mapper.toChatResponse(testCreatedChat);

        Mockito.when(chatService.createPersonalChat(request)).thenReturn(response);

        String chatCreateJson = objectMapper.writeValueAsString(request);
        mockMvc.perform(post("/chats/personal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(chatCreateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.userIds").isNotEmpty());
    }

    @Test
    void postCreateGroupChatTest() throws Exception {
        User firstTestUser = User.builder().id(1L).name("User1").email("testEmail1@gmail.com").build();
        User secondTestUser = User.builder().id(2L).name("User2").email("testEmail2@gmail.com").build();
        User thirdTestUser = User.builder().id(3L).name("User3").email("testEmail3@gmail.com").build();
        List<User> members = List.of(firstTestUser, secondTestUser, thirdTestUser);
        List<Long> memberIds = members.stream().map(User::getId).toList();
        Chat testCreatedChat = Chat.group("testChat", "description", members);
        testCreatedChat.setId(1L);
        CreateGroupChatRequest request = new CreateGroupChatRequest(testCreatedChat.getName(), testCreatedChat.getDescription(), memberIds);
        ChatResponse response = mapper.toChatResponse(testCreatedChat);

        Mockito.when(chatService.createGroupChat(request)).thenReturn(response);

        String chatCreateJson = objectMapper.writeValueAsString(request);
        mockMvc.perform(post("/chats/group")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(chatCreateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value(response.name()))
                .andExpect(jsonPath("$.description").value(response.description()))
                .andExpect(jsonPath("$.userIds").isNotEmpty());
    }
}
