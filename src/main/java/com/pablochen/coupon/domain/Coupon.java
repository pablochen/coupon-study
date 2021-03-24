package com.pablochen.coupon.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Coupon implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 21, nullable = false) //, unique = true)
    private String couponCode;

    @Column(name = "ASSIGNED")
    private boolean assigned;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @Column(name = "USED")
    private boolean used;

    @Column(name = "EXPIRED_AT", columnDefinition = "TIMESTAMP")
    private LocalDateTime expiredAt;

    @Builder
    public Coupon(String couponCode){
        LocalDateTime now = LocalDateTime.now();
        this.couponCode = couponCode;
        this.expiredAt = now.plusDays(3);
        this.assigned = false;
        this.user = null;
        this.used = false;
    }

    public void assignTo(User user){
        this.user = user;
        this.assigned = true;
    }

    public void use(){
        LocalDateTime now = LocalDateTime.now();
        if(now.isAfter(this.getExpiredAt())) throw new IllegalStateException("already expired coupon exception");
        this.used = true;
    }

    public void cancelUse(){
        this.used = false;
    }

}
