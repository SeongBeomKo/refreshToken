package com.example.springbootpracticeproject.config;

import com.example.springbootpracticeproject.token.JwtAuthenticationFilter;
import com.example.springbootpracticeproject.token.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // csrf 비활성화
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증이므로 세션 X
                .and()
                .authorizeRequests()
                .antMatchers("/member/signup", "/login").permitAll() // 검증 없이 접근 허용
                .antMatchers("/admin/**").hasRole("ADMIN") // ADMIN권한 접근 가능
                .antMatchers("/user/**").hasRole("USER") // USER권한 접근 가능
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class);
                // JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 전에 넣는다
    }
}
