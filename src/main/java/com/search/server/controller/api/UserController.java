package com.search.server.controller.api;

import com.search.server.dto.user.UserDto;
import com.search.server.dto.user.UserLoginResponseDto;
import com.search.server.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.header.Header;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 회원 컨트롤러
 * @version 1.0
 * @author jeonjihoon
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    /**
     * 회원 가입
     * @param request (아이디, 비밀번호)
     * @return (아이디)
     */

    @PostMapping("/signup")
    public ResponseEntity<UserDto.Response> signUp(@Valid @RequestBody UserDto.Request request) {
        log.info(request.getUserName() + " : " + request.getPassword());
        UserDto.Info joinUserName = userService.join(request);
        return new ResponseEntity<>(new UserDto.Response(joinUserName, 200, "success"),
                HttpStatus.OK);
    }

    /**
     *
     * @param request (아이디, 비밀번호)
     * @return Token + Roles
     */

    @PostMapping("/login")
    public UserLoginResponseDto login(@Valid @RequestBody UserDto.Request request) {
        log.info(request.getUserName() + " : " + request.getPassword());
        return userService.login(request);
    }
}
