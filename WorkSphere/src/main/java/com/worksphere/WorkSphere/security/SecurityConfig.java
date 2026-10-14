package com.worksphere.WorkSphere.security;

import com.worksphere.WorkSphere.tenant.TenantFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtAuthFilter jwtAuthFilter;

	// ADD THIS
	private final TenantFilter tenantFilter;

	private final CustomAccessDeniedHandler accessDeniedHandler;
	
	private final CustomAuthenticationEntryPoint authenticationEntryPoint;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable())

				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				.authorizeHttpRequests(auth -> auth

						.requestMatchers("/auth/**", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html","/projects/**","/project-members/**","/tasks/**","/task-attachments/**")
						.permitAll()

						.requestMatchers("/admin/**").hasRole("SUPER_ADMIN")

						.requestMatchers("/user/**").hasAnyRole("EMPLOYEE", "SUPER_ADMIN")

						.anyRequest().authenticated())
				.exceptionHandling(ex -> ex

				        .authenticationEntryPoint(authenticationEntryPoint)

				        .accessDeniedHandler(accessDeniedHandler)
				)

				// TENANT FILTER FIRST
				.addFilterBefore(tenantFilter, UsernamePasswordAuthenticationFilter.class)

				// JWT FILTER SECOND
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {

		return config.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}