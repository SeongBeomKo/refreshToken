package com.example.springbootpracticeproject.service;

import com.example.springbootpracticeproject.dto.UserDto;
import com.example.springbootpracticeproject.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.springbootpracticeproject.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void registerUser(UserDto userDto) {

        //id, 이메일 중복 체크 필요

        userRepository.save(User
                .builder()
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(userDto.getPassword()) //password encoding 아직 안함
                .build());
    }
}
