package com.pablochen.coupon.controller;

import com.pablochen.coupon.domain.Coupon;
import com.pablochen.coupon.domain.User;
import com.pablochen.coupon.dto.CouponDto;
import com.pablochen.coupon.service.CouponService;
import com.pablochen.coupon.service.UserService;
import com.pablochen.coupon.util.CodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin
public class AdminController {

    private final CouponService couponService;
    private final UserService userService;

    //1. 랜덤한 코드의 쿠폰을 N개 생성하여 데이터베이스에 보관하는 API를 구현하세요. = input : N
    @PostMapping("/addCoupons/{num}")
    public List<Coupon> addCoupons(@PathVariable int num){
        return IntStream.range(0, num)
               .mapToObj(i -> {
                   CouponDto couponDto = CouponDto.builder()
                           .couponCode(CodeGenerator.createCode())
                           .build();
                   return couponService.createCoupon(couponDto);
               })
               .collect(Collectors.toList());
    }

    //2. 생성된 쿠폰중 하나를 사용자에게 지급하는 API를 구현하세요. = output : 쿠폰번호(XXXXX-XXXXXX-XXXXXXXX)
    @PutMapping("/assignCoupon/{memberId}")
    public String  assignCoupon(@PathVariable String memberId){
        User user = userService.ReadUser(memberId);
        Coupon coupon = couponService.findUnassignedCoupon();
        coupon.assignTo(user);
        return couponService.updateCoupon(coupon).getCouponCode();
    }

    //3. 발급된 쿠폰중 당일 만료된 전체 쿠폰 목록을 조회하는 API를 구현하세요.
    @GetMapping("/getExpiredCouponList")
    public List<Coupon> getExpiredCouponList(){
        LocalDateTime today = LocalDateTime.now();
        return couponService.findExpiredCouponList(today);
    }


}