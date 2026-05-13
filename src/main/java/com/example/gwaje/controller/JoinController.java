package com.example.gwaje.controller;

import com.example.gwaje.dto.SignUpRequestDto;
import com.example.gwaje.entity.User;
import com.example.gwaje.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class JoinController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/join")
    public String joinForm() {
        return "join";
    }

    @PostMapping("/joinProc")
    public String joinProc(SignUpRequestDto signUpDto) { // User 대신 DTO로 받기
        System.out.println("회원가입 진행 ID : " + signUpDto.getUsername());

        // 1. DTO 데이터를 Entity로 옮기기
        User user = new User();
        user.setUsername(signUpDto.getUsername());

        // 2. 비밀번호 암호화 후 세팅
        String rawPassword = signUpDto.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);

        // 3. 권한 설정
        user.setRole("ROLE_USER");

        // 4. DB 저장
        userRepository.save(user);

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }
}