package com.search.server.controller.api;

import com.search.server.dto.UserDto;
import com.search.server.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<UserDto.Response> signUp(@RequestBody UserDto.Request request) {
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
    public String login(@RequestBody UserDto.Request request) {
        log.info(request.getUserName() + " : " + request.getPassword());
        return userService.login(request);
    }
}
