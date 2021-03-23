package com.search.server.config.property;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OpenApiPropertyTest {

    @Autowired
    private OpenApiProperty openApiProperty;

    @Test
    void 외부연동을_위한_설정정보_주입_테스트() {
        assertNotNull(openApiProperty.getNaverUri());
        assertNotNull(openApiProperty.getKakaoUri());
        assertNotNull(openApiProperty.getKakaoApiKey());
        assertNotNull(openApiProperty.getNaverClientId());
        assertNotNull(openApiProperty.getNaverClientSecret());
    }
}