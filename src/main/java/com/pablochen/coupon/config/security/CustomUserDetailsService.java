package com.pablochen.coupon.config.security;

import com.pablochen.coupon.domain.User;
import com.pablochen.coupon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =  userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        System.out.println("TIK1 : " + user.toString());
        System.out.println("TIK1 : " + ((UserDetails)user).getUsername());
        System.out.println("TIK1 : " + ((UserDetails)user).getPassword());

        return (UserDetails) user;
    }
}