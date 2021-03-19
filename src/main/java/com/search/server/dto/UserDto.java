package com.search.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * User 입출력 처리를 위한 클래스
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
        private String userName;
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
