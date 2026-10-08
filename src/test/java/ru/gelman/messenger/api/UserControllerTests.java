package ru.gelman.messenger.api;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.gelman.messenger.entity.User;
import ru.gelman.messenger.service.users.UserService;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class UserControllerTests {

    private static User testUser;
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void prepareUser() {
        testUser = new User();
        testUser.setName("David");
        testUser.setEmail("testEmail@gmail.com");
    }

    @Test
    void postCreateUserTest() throws Exception {
        User created = new User();
        created.setEmail(testUser.getEmail());
        created.setName(testUser.getName());
        created.setId(1L);
        Mockito.when(userService.create(testUser)).thenReturn(created);

        //String userCreateJson = "{\"name\":\"David\",\"email\":\"testEmail@gmail.com\"}";
        String userCreateJson = objectMapper.writeValueAsString(testUser);
        log.info(userCreateJson);
        mockMvc.perform(post("/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userCreateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("David"))
                .andExpect(jsonPath("$.email").value("testEmail@gmail.com"));

    }
}
