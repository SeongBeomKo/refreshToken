package com.example.springbootpracticeproject.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    @NotBlank(message = "ID는 공백일 수 없습니다.") // null, "", "  "  모두 허용하지않는다
    @Size(min = 4, message = "닉네임은 4자 이상 입력해주세요.")
    private String username;

    @Email(message = "올바른 이메일 형식이 아닙니다.") // null은 허용, 입력했을 경우 형식만 체크
    private String email;

    @NotBlank(message = "비밀번호는 공백일 수 없습니다.")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z]).{4,}",
            message = "비밀번호는 영문 대,소문자와 숫자가 포함된 4자 이상.")
    private String password;
}
