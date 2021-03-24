package com.search.server.config.property;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 시큐리티 프로퍼피
 * @version 1.0
 * @author jeonjihoon
 */

@Data
@Configuration
@ConfigurationProperties(prefix = "token")
public class TokenProperty {
    @Value("token.secret-key")
    private String tokenSecretKey;
}
