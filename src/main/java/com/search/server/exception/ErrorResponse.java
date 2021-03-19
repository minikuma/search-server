package com.search.server.exception;

import lombok.*;

/**
 * 에러 응답 처리
 * @version 1.0
 * @author jeonjihoon
 */

public class ErrorResponse {

    @Getter
    @AllArgsConstructor
    @Builder
    public static class Info {
        private final int errorStatus;
        private final String errorCode;
        private final String errorMessage;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response {
        private final Info info;
        private final int status;
        private final String message;
    }
}
