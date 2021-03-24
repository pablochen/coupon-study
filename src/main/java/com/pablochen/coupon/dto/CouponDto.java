package com.pablochen.coupon.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CouponDto {
    private String couponCode;
}
