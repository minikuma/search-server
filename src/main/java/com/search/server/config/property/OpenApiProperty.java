package com.search.server.config.property;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "external")
public class OpenApiProperty {
    @Value("${external.kakao.api.uri}")
    private String kakaoUri;

    @Value("${external.kakao.api.key}")
    private String kakaoApiKey;

    @Value("${external.naver.api}")
    private String naverUri;

    @Value("${external.naver.client-id}")
    private String naverClientId;

    @Value("${external.naver.client-secret}")
    private String naverClientSecret;
}
