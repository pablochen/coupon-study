package com.pablochen.coupon.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CouponDto {
    private String couponCode;
    private Boolean used;
    private LocalDateTime expiredAt;
}
