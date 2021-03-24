package com.search.server.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 토큰 정보
 * @version 1.0
 * @author jeonjihoon
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserLoginResponseDto {
    private String token;
}
