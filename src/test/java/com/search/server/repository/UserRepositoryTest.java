package com.search.server.repository;

import com.search.server.domain.User;
import com.search.server.exception.ErrorCode;
import com.search.server.exception.biz.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private static PasswordEncoder passwordEncoder;

    @BeforeAll
    void init() {
        passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Test
    void 회원_등록_조회_테스트() {
        // Encode 작업
        init();

        // given
        User user = User.builder()
                .userName("회원1")
                .password(passwordEncoder.encode("비번-1"))
                .build();
        // when
        userRepository.save(user);

        // then
        User findUser = userRepository.findById(user.getId()).orElseThrow(UserNotFoundException::new);

        log.info("input password = " + user.getPassword() + " : " + "find password = " + findUser.getPassword());

        assertEquals(user.getId(), findUser.getId());
        assertEquals(user.getUsername(), findUser.getUsername());
        assertEquals(user.getPassword(), findUser.getPassword());
    }

    @Test
    void 등록된_회원을_회원아이디로_조회() {
        // Encode 작업
        init();

        // given
        User user = User.builder()
                .userName("회원2")
                .password(passwordEncoder.encode("비번-2"))
                .build();

        // when
        userRepository.save(user);

        // then
        User findUser = userRepository.findByUserName(user.getUsername())
                .orElseThrow(UserNotFoundException::new);

        assertEquals(user.getId(), findUser.getId());
        assertEquals(user.getUsername(), findUser.getUsername());
        assertEquals(user.getPassword(), findUser.getPassword());
    }

    @Test
    void 등록된_회원_조회시_회원이_존재하지_않는경우_예외테스트() {
        // Encode 작업
        init();

        // given
        User user = User.builder()
                .userName("예외회원1")
                .password(passwordEncoder.encode("예외회원비번-1"))
                .build();

        // when
        userRepository.save(user);

        // then
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            userRepository.findByUserName("not exist")
                    .orElseThrow(UserNotFoundException::new);
        });

        log.info("code = " + exception.getErrorCode());

        assertEquals(exception.getErrorCode(), ErrorCode.NOT_FOUND);
        assertEquals(exception.getErrorCode().getMessage(), ErrorCode.NOT_FOUND.getMessage());
        assertEquals(exception.getErrorCode().getStatus(), ErrorCode.NOT_FOUND.getStatus());
    }
}