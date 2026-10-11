package ru.gelman.messenger.api;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.gelman.messenger.dto.CreateUserDto;
import ru.gelman.messenger.dto.UserDto;
import ru.gelman.messenger.entity.User;
import ru.gelman.messenger.service.user.UserInternalService;
import ru.gelman.messenger.service.user.UserService;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

    private static User testUser;
    private static CreateUserDto userInfo;
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private UserService userService;
    @MockitoBean
    private UserInternalService userInternalService;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void prepareUser() {
        testUser = User.builder().name("David").email("testemail@gmail.com").build();
        userInfo = new CreateUserDto(testUser.getName(), testUser.getEmail());
    }

    @Test
    void postCreateUserTest() throws Exception {
        UserDto created = new UserDto(1L, testUser.getName(), testUser.getEmail());
        Mockito.when(userService.create(userInfo)).thenReturn(created);

        String userCreateJson = objectMapper.writeValueAsString(testUser);
        mockMvc.perform(post("/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userCreateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value(testUser.getName()))
                .andExpect(jsonPath("$.email").value(testUser.getEmail()));

    }

    @Test
    void getUserInfoTest() throws Exception {
        long id = 1L;
        UserDto user = new UserDto(id, testUser.getName(), testUser.getEmail());
        Mockito.when(userService.findById(id)).thenReturn(user);

        mockMvc.perform(get("/users/" + id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(testUser.getName()))
                .andExpect(jsonPath("$.email").value(testUser.getEmail()));
    }

    @Test
    void getUserInfoListTest() throws Exception {
        List<UserDto> users = List.of(
                new UserDto(1L, testUser.getName(), testUser.getEmail()),
                new UserDto(2L, testUser.getName(), testUser.getEmail())
        );
        List<Long> ids = users.stream().map(UserDto::id).toList();
        String content = objectMapper.writeValueAsString(ids);
        Mockito.when(userService.findAllById(ids)).thenReturn(users);

        mockMvc.perform(get("/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty());
    }
}
