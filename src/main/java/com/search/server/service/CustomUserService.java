package com.search.server.service;

import com.search.server.exception.biz.UserNotFoundException;
import com.search.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 시큐리티 상세(사용자 조회) 서비스
 * @version 1.0
 * @author jeonjihoon
 */

@RequiredArgsConstructor
@Service
public class CustomUserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUserName(username)
                .orElseThrow(UserNotFoundException::new);
    }
}
