package com.pablochen.coupon.service;

import com.pablochen.coupon.domain.User;
import com.pablochen.coupon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User ReadUser(String memberId){
        return userRepository.findByUserId(memberId).orElseThrow(() -> new IllegalArgumentException("user not found excpetion"));
    }

}
