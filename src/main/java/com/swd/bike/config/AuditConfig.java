package com.swd.bike.config;

import com.swd.bike.entity.Account;
import com.swd.bike.repository.AccountRepository;
import com.swd.bike.service.ContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@RequiredArgsConstructor
public class AuditConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl();
    }

    private class AuditorAwareImpl implements AuditorAware<String> {

        @Autowired
        private ContextService contextService;

        @Autowired
        private AccountRepository accountRepository;

        @Override
        public Optional<String> getCurrentAuditor() {

            try {
                return contextService.getLoggedInUsername();
            } catch (Exception ignore) {
            }
            return Optional.empty();
        }
    }
}