package com.example.springbootpracticeproject.controller;

import com.example.springbootpracticeproject.dto.UserDto;
import com.example.springbootpracticeproject.exception.InputValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.springbootpracticeproject.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // @Valid가 선언되어 있으므로 User 클래스 속성의 제약조건에 따라 데이터 유효성을 검사한다.
    @PostMapping("/member/signup")
    public void signup(@RequestBody @Valid UserDto userDto, BindingResult bindingResult) {

        //유효성 검사 실패 시 exception resolver에서 상태코드를 400으로 바꿔서 메시지와 함께 내려준다
        InputValidator.BadRequestHandler(bindingResult);
        userService.registerUser(userDto);
    }
}
