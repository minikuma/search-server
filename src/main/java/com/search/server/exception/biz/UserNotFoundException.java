package com.search.server.exception.biz;

import com.search.server.exception.CustomException;
import com.search.server.exception.ErrorCode;

/**
 * 사용자 정보 미 존재 예외 처리
 * @version 1.0
 * @author jeonjihoon
 */

public class UserNotFoundException extends CustomException {
    public UserNotFoundException() {
        super(ErrorCode.NOT_FOUND);
    }
}
