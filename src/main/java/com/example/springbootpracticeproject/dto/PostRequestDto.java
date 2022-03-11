package com.example.springbootpracticeproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostRequestDto {

    @NotBlank(message = "내용은 공백일 수 없습니다.")
    private String content;
}
