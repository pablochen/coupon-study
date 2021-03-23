package com.pablochen.coupon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@Configuration
@EnableJpaRepositories("com.pablochen.coupon.repository")
public class JpaConfig{
    @Bean
    public AuditorAware<String> auditorProvider() {
        return new SpringSecurityAuditor();
    }
}
