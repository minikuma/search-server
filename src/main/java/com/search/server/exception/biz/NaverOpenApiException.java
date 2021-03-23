package com.search.server.exception.biz;

import com.search.server.exception.CustomException;
import com.search.server.exception.ErrorCode;

/**
 * 외부 연동 예외 처리 (NAVER)
 * @version 1.0
 * @author jeonjihoon
 *
 */

public class NaverOpenApiException extends CustomException {
    public NaverOpenApiException() {
        super(ErrorCode.NAVER_API);
    }
}
