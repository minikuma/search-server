package com.search.server.exception;

/**
 * 에러 코드
 * @version 1.0
 * @author jeonjihoon
 */

public enum ErrorCode {

    DUP(1000, "D001", "이미 가입되었습니다.");

    private final String code;
    private final String message;
    private final int status;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    ErrorCode(int status, String code, String message) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
