package com.search.server.service;

import com.search.server.domain.User;
import com.search.server.dto.UserDto;
import com.search.server.exception.biz.DuplicationSignUpException;
import com.search.server.repository.UserRepository;
import com.search.server.util.PasswordEncryption;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public UserDto.Info join(UserDto.Request request) {
        String enPassword = PasswordEncryption.encode(request.getPassword());
        Optional<User> findUser = userRepository.findUserByUserNameAndPassword(request.getUserName(), enPassword);

        // TODO: Optional null 체크 가능?
        if (findUser.isPresent()) {
            throw new DuplicationSignUpException();
        }

        User user = User.builder()
                .userName(request.getUserName())
                .password(PasswordEncryption.encode(request.getPassword()))
                .build();

        User saveUser = userRepository.save(user);

        return UserDto.Info.builder()
                .userName(saveUser.getUserName())
                .build();
    }
}
