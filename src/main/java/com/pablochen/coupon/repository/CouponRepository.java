package com.pablochen.coupon.repository;

import com.pablochen.coupon.domain.Coupon;
import com.pablochen.coupon.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    public Optional<Coupon> findByCouponCode(String couponCode);
    public Optional<Coupon> findTop1ByAssigned(boolean assigned);
    public List<Coupon> findAllByExpiredAtLessThan(LocalDateTime now);
    public List<Coupon> findAllByUser(User user);
}
