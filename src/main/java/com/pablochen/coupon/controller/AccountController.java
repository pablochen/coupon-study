package com.pablochen.coupon.controller;

import com.pablochen.coupon.aop.PerfLogging;
import com.pablochen.coupon.config.security.JwtTokenProvider;
import com.pablochen.coupon.domain.User;
import com.pablochen.coupon.dto.UserDto;
import com.pablochen.coupon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
@CrossOrigin
public class AccountController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("")
    public String accountTest(){
        return "account";
    }

    @PerfLogging
    @PostMapping("/signUp")
    public User signUp(@RequestBody UserDto userDto) {
        return userRepository.save(User.builder()
                .userId(userDto.getUserId())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .roles(Collections.singletonList("ROLE_" + userDto.getRole()))
                .build());
    }

    @PerfLogging
    @PostMapping("/signIn")
    public String signIn(@RequestBody UserDto userDto) {
        User member = userRepository.findByUserId(userDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));

        if (!passwordEncoder.matches(userDto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        return jwtTokenProvider.createToken(member.getUserId(), member.getRoles());
    }




}