package com.worksphere.WorkSphere.security;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader =
                request.getHeader("Authorization");

        // NO JWT TOKEN PRESENT
        if (authHeader == null
                || !authHeader.startsWith("Bearer ")) {

            log.warn(
                    "No JWT token found for request: {}",
                    request.getRequestURI()
            );

            filterChain.doFilter(request, response);

            return;
        }

        try {

            // REMOVE "Bearer "
            String token =
                    authHeader.substring(7);

            log.info("JWT token received");

            // EXTRACT DETAILS
            String email =
                    jwtUtil.extractEmail(token);

            Long userId =
                    jwtUtil.extractUserId(token);

            // CHECK IF USER NOT ALREADY AUTHENTICATED
            if (email != null &&
                    SecurityContextHolder
                            .getContext()
                            .getAuthentication() == null) {

                log.info(
                        "Authenticating user: {}",
                        email
                );

                var userDetails =
                        userDetailsService
                                .loadUserByUsername(email);

                // VALIDATE TOKEN
                if (jwtUtil.validateToken(token)) {

                    log.info(
                            "JWT token validated successfully for user: {}",
                            email
                    );

                    AuthenticatedUser principal =
                            new AuthenticatedUser(
                                    userId,
                                    email
                            );

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    principal,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    authToken.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request)
                    );

                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(authToken);

                    log.info(
                            "SecurityContext updated for user: {}",
                            email
                    );

                } else {

                    log.warn(
                            "Invalid JWT token for user: {}",
                            email
                    );
                }
            }

        } catch (Exception exception) {

            log.error(
                    "JWT Authentication failed: {}",
                    exception.getMessage()
            );
        }

        filterChain.doFilter(request, response);
    }
}