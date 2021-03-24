package com.search.server.exception.biz;

import com.search.server.exception.CustomException;
import com.search.server.exception.ErrorCode;

/**
 * 패드워드 불 일치
 * @version 1.0
 * @author jeonjihoon
 */

public class WrongPasswordException extends CustomException {
    public WrongPasswordException() {
        super(ErrorCode.WRONG_PASSWORD);
    }
}
