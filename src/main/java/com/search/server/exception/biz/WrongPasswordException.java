package com.search.server.exception.biz;

import com.search.server.exception.CustomException;
import com.search.server.exception.ErrorCode;

public class WrongPasswordException extends CustomException {
    public WrongPasswordException() {
        super(ErrorCode.WRONG_PASSWORD);
    }
}
