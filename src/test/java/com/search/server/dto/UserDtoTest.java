package com.search.server.dto;

import com.search.server.dto.user.UserDto;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDtoTest {

    @Test
    void 회원등록시_username_password_필수값_테스트_NOT_BLANK() {
        // given
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        UserDto.Request request = new UserDto.Request();

        request.setUserName(" ");
        request.setPassword(null);

        // when
        Set<ConstraintViolation<UserDto.Request>> violations = validator.validate(request);

        // then
        assertEquals(violations.size(), 2);
    }
}