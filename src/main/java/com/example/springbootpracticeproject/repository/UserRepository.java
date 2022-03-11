package com.example.springbootpracticeproject.repository;

import com.example.springbootpracticeproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    //data 가 많아지면 indexing 필요
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
