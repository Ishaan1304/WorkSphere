package com.worksphere.WorkSphere.audit;

import com.worksphere.WorkSphere.security.AuthenticatedUser;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditorProvider")
public class AuditorAwareImpl
        implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {

            return Optional.empty();
        }

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof AuthenticatedUser user)) {
            return Optional.empty();
        }

        return Optional.of(user.id());
    }
}