package com.search.server.exception.biz;

import com.search.server.exception.CustomException;
import com.search.server.exception.ErrorCode;

public class UserNotFoundException extends CustomException {
    public UserNotFoundException() {
        super(ErrorCode.NOT_FOUND);
    }
}
