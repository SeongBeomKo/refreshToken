package com.example.springbootpracticeproject.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private String password;

    //user가 삭제 되면 관련 post가 전부 삭제된다
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Post> postList;
}
