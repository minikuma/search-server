package com.search.server.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 사용자 관리를 위한 클래스
 * @version 1.0
 * @author jeonjihoon
 */

public class UserDto {

    @Getter
    @AllArgsConstructor
    @Builder
    public static class Info {
        private final String userName;
    }

    @Getter
    @Setter
    public static class Request {
        @NotBlank(message = "사용자 이름은 필수값 입니다.")
        private String userName;
        @NotBlank(message = "비밀번호는 필수값 입니다.")
        private String password;
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private final Info info;
        private final int status;
        private final String message;
    }
}
