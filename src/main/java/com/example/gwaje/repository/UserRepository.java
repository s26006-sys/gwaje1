package com.example.gwaje.repository;

import com.example.gwaje.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 아이디(username)로 사용자 정보를 가져오는 메서드
    Optional<User> findByUsername(String username);

    // 이메일(email)로 사용자 정보를 가져오는 메서드
    Optional<User> findByEmail(String email);
}