package com.example.springbootpracticeproject.service;

import com.example.springbootpracticeproject.dto.PostRequestDto;
import com.example.springbootpracticeproject.dto.PostResponseDto;
import com.example.springbootpracticeproject.entity.Post;
import com.example.springbootpracticeproject.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public PostResponseDto createPost(PostRequestDto postDto) {

        //user는 session으로 가져와서 넣기
        Post post = postRepository.save(Post.builder()
                .content(postDto.getContent())
                .build());

        return PostResponseDto.builder()
                .id(post.getId())
                .build();
    }

    public PostResponseDto getPost(Long id) {

        Post post = postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("게시글이 없습니다"));

        return PostResponseDto.builder()
                .id(post.getId())
                .content(post.getContent())
                .username(post.getUser().getUsername())
                .build();

    }

    public List<PostResponseDto> getAllPost() {
        //Post -> PostResponseDto 변환
        return postRepository.findAll()
                .stream()
                .map(p -> PostResponseDto.builder()
                .id(p.getId())
                .username(p.getUser().getUsername())
                .content(p.getContent())
                .build())
                .collect(Collectors.toList());
    }

    //Transactional을 넣어야하는 이유
    @Transactional
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
