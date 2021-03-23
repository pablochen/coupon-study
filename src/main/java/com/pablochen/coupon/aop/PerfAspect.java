package com.pablochen.coupon.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class PerfAspect {
    @Around("@annotation(PerfLogging)")
    public Object logPefrf(ProceedingJoinPoint pjp) throws Throwable {
        long begin = System.currentTimeMillis();

        Object retVal = pjp.proceed();

        System.out.println("execution time : " + (System.currentTimeMillis() - begin));
        return retVal;
    }
}
