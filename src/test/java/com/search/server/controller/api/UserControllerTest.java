package com.search.server.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.search.server.dto.UserDto;
import com.search.server.exception.ErrorCode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext ctx;

    @BeforeAll
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    void 회원가입테스트() throws Exception {
        setUp();
        UserDto.Request user = new UserDto.Request();
        user.setUserName("회원-1");
//        user.setPassword(PasswordEncryption.encode("비밀번호-1"));

        ObjectMapper objectMapper = new ObjectMapper();

        mvc.perform(post("/api/v1/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.info.userName").value(user.getUserName()));
    }

    @Test
    void 회원중복가입테스트() throws Exception {
        setUp();
        UserDto.Request user = new UserDto.Request();
        user.setUserName("회원-2");
//        user.setPassword(PasswordEncryption.encode("비밀번호-2"));

        ObjectMapper objectMapper = new ObjectMapper();

        mvc.perform(post("/api/v1/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.info.userName").value(user.getUserName()));

        mvc.perform(post("/api/v1/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.info.errorMessage").value(ErrorCode.DUP.getMessage()));
    }
}