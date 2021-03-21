package com.search.server.service;

import com.search.server.dto.user.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void 회원정보_저장_테스트() {
        // given
        UserDto.Request request = new UserDto.Request();
        request.setUserName("k");
        request.setPassword(passwordEncoder.encode("kako"));

        // when
        UserDto.Info joinUser = userService.join(request);

        // then
        assertEquals(joinUser.getUserName(), request.getUserName());
    }
}