package com.example.springbootpracticeproject.repository;

import com.example.springbootpracticeproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
