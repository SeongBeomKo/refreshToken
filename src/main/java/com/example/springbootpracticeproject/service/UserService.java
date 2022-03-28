package com.example.springbootpracticeproject.service;

import com.example.springbootpracticeproject.dto.UserDto;
import com.example.springbootpracticeproject.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.springbootpracticeproject.repository.UserRepository;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(UserDto userDto) {

        //id, 이메일 중복 체크 필요

        userRepository.save(User
                .builder()
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .roles(Collections.singletonList("ROLE_USER"))
                .build());
    }

    public User findUser(UserDto user) {
        User member = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("아이디 혹은 비밀번호가 잘못되었습니다."));
        return member;
    }

    public boolean checkPassword(User member, UserDto user) {
        return passwordEncoder.matches(user.getPassword(), member.getPassword());
    }


}
