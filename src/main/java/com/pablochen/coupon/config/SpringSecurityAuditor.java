package com.pablochen.coupon.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SpringSecurityAuditor implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (null == authentication || !authentication.isAuthenticated())
        {
            return null;
        }

        return Optional.of((String)authentication.getPrincipal());
    }
}
