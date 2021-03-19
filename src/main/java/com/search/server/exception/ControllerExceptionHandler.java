package com.search.server.exception;

import com.search.server.exception.biz.DuplicationSignUpException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = RestController.class)
public class ControllerExceptionHandler {

    @ExceptionHandler(DuplicationSignUpException.class)
    protected ResponseEntity<ErrorResponse.Response> handlerDupSignUpException(CustomException e) {

        ErrorCode errorCode = e.getErrorCode();

        ErrorResponse.Info info = ErrorResponse.Info.builder()
                .errorCode(errorCode.getCode())
                .errorStatus(errorCode.getStatus())
                .errorMessage(errorCode.getMessage())
                .build();

        ErrorResponse.Response response = new ErrorResponse.Response(info, 200, "success");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
