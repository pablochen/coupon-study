package com.pablochen.coupon.controller;

import com.pablochen.coupon.config.SpringSecurityAuditor;
import com.pablochen.coupon.domain.Coupon;
import com.pablochen.coupon.dto.CouponDto;
import com.pablochen.coupon.service.CouponService;
import com.pablochen.coupon.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
@CrossOrigin
public class MemberController {
    private final SpringSecurityAuditor springSecurityAuditor = new SpringSecurityAuditor();
    private final UserService userService;
    private final CouponService couponService;


    //1. 사용자에게 지급된 쿠폰을 조회하는 API를 구현하세요. (for self) = input : id(from key)
    @GetMapping("/getCouponList")
    public List<CouponDto> getCouponList(){
        String userId = springSecurityAuditor.getCurrentAuditor().orElseThrow(() -> new IllegalStateException("have to signIn exception"));
        return couponService.findAssignedCouponList(userId).stream()
                .map(item -> {
                    return CouponDto.builder()
                            .couponCode(item.getCouponCode())
                            .used(item.isUsed())
                            .expiredAt(item.getExpiredAt())
                            .build();
                })
                .collect(Collectors.toList());
    }

    //2. 지급된 쿠폰중 하나를 사용하는 API를 구현하세요. (쿠폰 재사용은 불가) = input : 쿠폰번호
    @PutMapping("/useCoupon/{couponCode}")
    public CouponDto useCoupon(@PathVariable String couponCode){
        Coupon coupon = couponService.findCoupon(couponCode);
        if(coupon.isUsed()) throw new IllegalAccessError("used Coupon exception");

        coupon.use();
        couponService.updateCoupon(coupon);

        return CouponDto.builder().couponCode(coupon.getCouponCode()).used(coupon.isUsed()).expiredAt(coupon.getExpiredAt()).build();
    }

    //3. 지급된 쿠폰중 하나를 사용 취소하는 API를 구현하세요. (취소된 쿠폰 재사용 가능) = input : 쿠폰번호
    @PutMapping("/cancelCoupon/{couponCode}")
    public CouponDto cancelCoupon(@PathVariable String couponCode){
        Coupon coupon = couponService.findCoupon(couponCode);
        if(!coupon.isUsed()) throw new IllegalAccessError("unused Coupon exception");

        coupon.cancelUse();
        couponService.updateCoupon(coupon);

        return CouponDto.builder().couponCode(coupon.getCouponCode()).used(coupon.isUsed()).expiredAt(coupon.getExpiredAt()).build();
    }

}