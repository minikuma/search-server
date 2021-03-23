package com.search.server.config.property;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TokenPropertyTest {

    @Autowired
    private TokenProperty tokenProperty;

    @Test
    void 시큐리티_토큰_설정정보_주입_테스트() {
        Assertions.assertNotNull(tokenProperty.getTokenSecretKey());
    }
}