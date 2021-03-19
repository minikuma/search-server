package com.search.server.repository;

import com.search.server.domain.User;
import com.search.server.util.PasswordEncryption;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;


    @Test
    void 회원_등록_조회_테스트() {
        // given
        User user = User.builder()
                .userName("회원1")
                .password(PasswordEncryption.encode("비번-1"))
                .build();
        // when
        userRepository.save(user);

        // then
        User findUser = userRepository.findById(user.getUserId()).orElseThrow(NoSuchElementException::new);

        assertEquals(user.getUserId(), findUser.getUserId());
        assertEquals(user.getUserName(), findUser.getUserName());
        assertEquals(user.getPassword(), findUser.getPassword());
    }

    @Test
    void 회원_등록_회원아이디_패스워드기준으로_조회() {
        // given
        User user = User.builder()
                .userName("회원2")
                .password(PasswordEncryption.encode("비번-2"))
                .build();

        // when
        userRepository.save(user);

        // then
        User findUser = userRepository
                .findUserByUserNameAndPassword(user.getUserName(), user.getPassword())
                .orElseThrow(NoSuchElementException::new);

        assertEquals(user.getUserId(), findUser.getUserId());
        assertEquals(user.getUserName(), findUser.getUserName());
        assertEquals(user.getPassword(), findUser.getPassword());
    }
}