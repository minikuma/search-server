package com.search.server.exception;

import com.search.server.exception.biz.DuplicationSignUpException;
import com.search.server.exception.biz.UserNotFoundException;
import com.search.server.exception.biz.WrongPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = RestController.class)
public class ControllerExceptionHandler {

    @ExceptionHandler(DuplicationSignUpException.class)
    protected ResponseEntity<ErrorResponse.Response> handlerDupSignUpException(CustomException e) {
        return getResponseEntity(e);
    }

    @ExceptionHandler(WrongPasswordException.class)
    protected ResponseEntity<ErrorResponse.Response> handlerWrongPasswordException(CustomException e) {
        return getResponseEntity(e);
    }

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<ErrorResponse.Response> handlerUserNotFoundException(CustomException e) {
        return getResponseEntity(e);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse.Response> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return getGlobalResponseEntity(e);
    }

    private ResponseEntity<ErrorResponse.Response> getResponseEntity(CustomException e) {
        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse.Info info = ErrorResponse.Info.builder()
                .errorCode(errorCode.getCode())
                .errorStatus(errorCode.getStatus())
                .errorMessage(errorCode.getMessage())
                .build();
        ErrorResponse.Response response = new ErrorResponse.Response(info, 200, "success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private ResponseEntity<ErrorResponse.Response> getGlobalResponseEntity(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();

        ErrorResponse.Info info = ErrorResponse.Info.builder()
                .errorCode(ErrorCode.PARAMETER_NOT_FOUND.getCode())
                .errorStatus(ErrorCode.PARAMETER_NOT_FOUND.getStatus())
                .errorMessage(errorMessage)
                .build();
        ErrorResponse.Response response = new ErrorResponse.Response(info, 400, "Bad Request");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
