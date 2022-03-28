package com.example.springbootpracticeproject.controller;

import com.example.springbootpracticeproject.dto.UserDto;
import com.example.springbootpracticeproject.entity.RefreshToken;
import com.example.springbootpracticeproject.entity.User;
import com.example.springbootpracticeproject.exception.InputValidator;
import com.example.springbootpracticeproject.repository.TokenRepository;
import com.example.springbootpracticeproject.service.RedisService;
import com.example.springbootpracticeproject.token.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.springbootpracticeproject.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final TokenRepository tokenRepository;
    private final RedisService redisService;

    // @Valid가 선언되어 있으므로 User 클래스 속성의 제약조건에 따라 데이터 유효성을 검사한다.
    @PostMapping("/member/signup")
    public void signup(@RequestBody @Valid UserDto userDto, BindingResult bindingResult) {

        //유효성 검사 실패 시 exception resolver에서 상태코드를 400으로 바꿔서 메시지와 함께 내려준다
        InputValidator.BadRequestHandler(bindingResult);
        userService.registerUser(userDto);
    }

    // 로그인
    @PostMapping("/login")
    public void login(@RequestBody UserDto userDto, HttpServletResponse response) {
        // 유저 존재 확인
        User member = userService.findUser(userDto);
        boolean checkResult = userService.checkPassword(member, userDto);
        // 비밀번호 체크
        if(!checkResult) {
            throw new IllegalArgumentException("아이디 혹은 비밀번호가 잘못되었습니다.");
        }
        // 어세스, 리프레시 토큰 발급 및 헤더 설정
        String accessToken = jwtTokenProvider.createAccessToken(member.getEmail(), member.getRoles());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getEmail(), member.getRoles());
        jwtTokenProvider.setHeaderAccessToken(response, accessToken);
        jwtTokenProvider.setHeaderRefreshToken(response, refreshToken);
        // Redis 인메모리에 리프레시 토큰 저장
        redisService.setValues(refreshToken, member.getEmail());
//        // 리프레시 토큰 저장소에 저장
//        tokenRepository.save(new RefreshToken(refreshToken));
    }

    // 로그아웃
    @GetMapping("/api/logout")
    public ResponseEntity logout(HttpServletRequest request) {
        redisService.delValues(request.getHeader("refreshToken"));
        return ResponseEntity.ok().body("로그아웃 성공!");
    }
}
