package com.pablochen.coupon.service;

import com.pablochen.coupon.domain.Coupon;
import com.pablochen.coupon.domain.User;
import com.pablochen.coupon.dto.CouponDto;
import com.pablochen.coupon.repository.CouponRepository;
import com.pablochen.coupon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponService {
    private final CouponRepository couponRepository;
    private final UserRepository userRepository;

    public Coupon createCoupon (CouponDto couponDto){
        Coupon coupon = Coupon.builder()
                .couponCode(couponDto.getCouponCode())
                .build();

        return couponRepository.save(coupon);
    }

    public Coupon updateCoupon (Coupon coupon){
        return couponRepository.save(coupon);
    }

    public Coupon findUnassignedCoupon(){
        return couponRepository.findTop1ByAssigned(false).orElseThrow(() -> new IllegalArgumentException("coupon not found exception"));
    }

    public List<Coupon> findExpiredCouponList(LocalDateTime localDateTime){
        return couponRepository.findAllByExpiredAtLessThan(localDateTime);
    }

    public List<Coupon> findAssignedCouponList(String memberId){
        User user = userRepository.findByUserId(memberId).orElseThrow(() -> new IllegalArgumentException("user not found exception"));
        return couponRepository.findAllByUser(user);
    }

    public Coupon findCoupon(String couponCode){
        return couponRepository.findByCouponCode(couponCode).orElseThrow(() -> new IllegalArgumentException("coupon not found exception"));
    }


}
