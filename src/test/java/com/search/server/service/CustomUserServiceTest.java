package com.search.server.service;

import com.search.server.domain.User;
import com.search.server.exception.ErrorCode;
import com.search.server.exception.biz.UserNotFoundException;
import com.search.server.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class CustomUserServiceTest {
    @Autowired
    private CustomUserService customUserService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void 사용자이름_기준으로_조회한다() {
        // given
        User user = User.builder()
                .userName("회원정보")
                .password("good!")
                .build();
        Optional<User> findUser = userRepository.findByUserName(user.getUsername());

        if (findUser.isPresent()) {
            // when
            UserDetails findUserDetail = customUserService.loadUserByUsername(findUser.get().getUsername());

            // then
            assertEquals(findUserDetail.getUsername(), user.getUsername());
        } else {
            assertNotNull(findUser); // fail!
        }
    }

    @Test
    void 사용자가_존재하지_않는경우_테스트() {

        // given
        User user = User.builder()
                .userName("회원정보")
                .password("good!")
                .build();

        Optional<User> findUser = userRepository.findByUserName(user.getUsername());

        if (findUser.isPresent()) {
            // when
            UserDetails findUserDetail = customUserService.loadUserByUsername(findUser.get().getUsername());
            assertEquals(findUserDetail.getUsername(), user.getUsername());
        } else {
            // then
            UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
                customUserService.loadUserByUsername("사용자없음");
            });

            log.info("exception: " + exception.getMessage());

            assertEquals(exception.getMessage(), ErrorCode.NOT_FOUND.getMessage());
        }
    }
}