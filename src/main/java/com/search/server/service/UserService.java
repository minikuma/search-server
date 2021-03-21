package com.search.server.service;

import com.search.server.config.security.JwtTokenProvider;
import com.search.server.domain.User;
import com.search.server.dto.user.UserDto;
import com.search.server.dto.user.UserLoginResponseDto;
import com.search.server.exception.biz.DuplicationSignUpException;
import com.search.server.exception.biz.UserNotFoundException;
import com.search.server.exception.biz.WrongPasswordException;
import com.search.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

/**
 * User 처리 로직
 * @version 1.0
 * @author jeonjihoon
 */

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDto.Info join(UserDto.Request request) {
        Optional<User> findUser = userRepository.findByUserName(request.getUserName());

        // TODO: Optional null 체크 가능?
        if (findUser.isPresent()) {
            throw new DuplicationSignUpException();
        }

        User user = User.builder()
                .userName(request.getUserName())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Collections.singletonList("ROLE_USER"))
                .build();

        User saveUser = userRepository.save(user);

        return UserDto.Info.builder()
                .userName(saveUser.getUsername())
                .build();
    }

    @Transactional
    public UserLoginResponseDto login(UserDto.Request request) {
        User findUser = userRepository.findByUserName(request.getUserName())
                .orElseThrow(UserNotFoundException::new);
        if (!passwordEncoder.matches(request.getPassword(), findUser.getPassword())) {
            throw new WrongPasswordException();
        }
        String token = jwtTokenProvider.createToken(findUser.getUsername(), findUser.getRoles());
        return UserLoginResponseDto.builder().token(token).build();
    }
}
