package com.search.server.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.search.server.dto.user.UserDto;
import com.search.server.exception.ErrorCode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeAll
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    void 회원가입시_입력파라미터_사용자이름_테스트() throws Exception {
        setUp();
        UserDto.Request user = new UserDto.Request();
        user.setUserName("");
        user.setPassword(passwordEncoder.encode("예외비밀번호-1"));

        ObjectMapper objectMapper = new ObjectMapper();

        mvc.perform(post("/api/v1/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void 회원가입시_입력파라미터_비밀번호_테스트() throws Exception {
        setUp();
        UserDto.Request user = new UserDto.Request();
        user.setUserName("예외-2");
        user.setPassword(null);

        ObjectMapper objectMapper = new ObjectMapper();

        mvc.perform(post("/api/v1/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void 회원가입테스트_로그인_패스워드_파라미터_테스트() throws Exception {
        setUp();

        // given
        UserDto.Request user = new UserDto.Request();
        user.setUserName("회원-3");
        user.setPassword(passwordEncoder.encode("비밀번호-3"));

        ObjectMapper objectMapper = new ObjectMapper();

        mvc.perform(post("/api/v1/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.info.userName").value(user.getUserName()));

        // when
        UserDto.Request request = new UserDto.Request();
        request.setUserName("회원-3");
        request.setPassword(null);

        // then
        mvc.perform(post("/api/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.info.errorCode").value(ErrorCode.PARAMETER_NOT_FOUND.getCode()));
    }

    @Test
    void 회원가입테스트_로그인_사용자이름_파라미터_테스트() throws Exception {
        setUp();

        // given
        UserDto.Request user = new UserDto.Request();
        user.setUserName("회원-4");
        user.setPassword(passwordEncoder.encode("비밀번호-4"));

        ObjectMapper objectMapper = new ObjectMapper();

        mvc.perform(post("/api/v1/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.info.userName").value(user.getUserName()));

        // when
        UserDto.Request request = new UserDto.Request();
        request.setUserName(null);
        request.setPassword("비밀번호-4");

        // then
        mvc.perform(post("/api/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.info.errorCode").value(ErrorCode.PARAMETER_NOT_FOUND.getCode()));
    }

    @Test
    void 회원중복가입테스트() throws Exception {
        setUp();
        UserDto.Request user = new UserDto.Request();
        user.setUserName("회원-5");
        user.setPassword(passwordEncoder.encode("비밀번호-5"));

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

    @Test
    void 회원가입이후_로그인_테스트() throws Exception {
        // given
        setUp();
        UserDto.Request user = new UserDto.Request();
        user.setUserName("회원-6");
        user.setPassword(passwordEncoder.encode("비밀번호-6"));

        ObjectMapper objectMapper = new ObjectMapper();

        mvc.perform(post("/api/v1/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.info.userName").value(user.getUserName()));

        // when
        mvc.perform(post("/api/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isOk());
    }
}