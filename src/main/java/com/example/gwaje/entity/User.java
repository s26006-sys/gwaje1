package com.example.gwaje.entity;

import jakarta.persistence.*;
import lombok.Data; // 만약 Lombok이 설정되어 있다면 @Data를 쓰면 더 편합니다.
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Setter
@Table(name = "user_table") // user는 DB 예약어인 경우가 많아 이름을 지정해주는게 안전합니다.
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    // 과제 목적상 필수값이 아니므로 제약조건을 제거했습니다.
    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles;

    private boolean enabled = true;

    // --- 추가된 필드 설정용 Setter (회원가입 로직에서 사용) ---

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // --- UserDetails 필수 구현 메서드 ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public void setRole(String roleUser) {
    }
}