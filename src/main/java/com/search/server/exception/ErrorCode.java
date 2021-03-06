package com.search.server.exception;

/**
 * 에러 코드
 * @version 1.0
 * @author jeonjihoon
 */

public enum ErrorCode {

    DUP(1000, "D001", "이미 가입되었습니다."),
    NOT_FOUND(2000, "D002", "사용자를 찾을 수 없습니다."),
    WRONG_PASSWORD(3000, "D003", "잘못된 패스워드 입니다."),
    PARAMETER_NOT_FOUND(4000, "D004", "필수 파라미터 오류"),
    KAKAO_API(5000, "D005", "KAKAO API 가 원활하지 않습니다."),
    NAVER_API(6000, "D006", "NAVER API 가 원활하지 않습니다.");

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
