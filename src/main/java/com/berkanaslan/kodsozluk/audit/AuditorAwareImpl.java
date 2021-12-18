package com.berkanaslan.kodsozluk.audit;

import com.berkanaslan.kodsozluk.model.Principal;
import com.berkanaslan.kodsozluk.model.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        String username = "";

        if (SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof Principal) {
            username = ((Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        }

        if (username.isEmpty()) {
            username = User.SYSTEM;
        }

        return Optional.of(username);
    }
}
