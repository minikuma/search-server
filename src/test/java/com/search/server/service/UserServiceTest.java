package com.search.server.service;

import com.search.server.dto.UserDto;
import com.search.server.util.PasswordEncryption;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void 회원정보_저장_테스트() {
        // given
        UserDto.Request request = new UserDto.Request();
        request.setUserName("k");
        request.setPassword(PasswordEncryption.encode("kako"));

        // when
        UserDto.Info joinUser = userService.join(request);

        // then
        assertEquals(joinUser.getUserName(), request.getUserName());
    }
}