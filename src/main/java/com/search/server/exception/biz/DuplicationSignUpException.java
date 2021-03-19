package com.search.server.exception.biz;

import com.search.server.exception.CustomException;
import com.search.server.exception.ErrorCode;

/**
 * 중복 가입 회원 예외 처리
 * @version 1.0
 * @author jeonjihoon
 *
 */

public class DuplicationSignUpException extends CustomException {
    public DuplicationSignUpException() {
        super(ErrorCode.DUP);
    }
}
