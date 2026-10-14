package com.worksphere.WorkSphere.tenant;

import jakarta.servlet.FilterChain;
import lombok.extern.slf4j.Slf4j;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class TenantFilter extends OncePerRequestFilter {

	private final TenantResolver tenantResolver;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		long startTime = System.currentTimeMillis();

		try {

			String method = request.getMethod();

			String path = request.getRequestURI();

			String ipAddress = request.getRemoteAddr();

			if (

			path.startsWith("/swagger-ui")

					|| path.startsWith("/v3/api-docs")

					|| path.startsWith("/swagger-resources")

					|| path.startsWith("/webjars")

			) {

				filterChain.doFilter(request, response);

				return;
			}

			String tenant = tenantResolver.resolveTenant(request);

			System.out.println(tenant);

			TenantContext.setTenant(tenant);

			// REQUEST LOGGING
			log.info("""
					==========================================
					REQUEST RECEIVED
					Method      : {}
					URI         : {}
					Tenant      : {}
					IP Address  : {}
					==========================================
					""", method, path, tenant, ipAddress);

			// CONTINUE FILTER CHAIN

			filterChain.doFilter(request, response);

		} finally {

			long endTime = System.currentTimeMillis();

			long totalTime = endTime - startTime;

			// RESPONSE LOGGING
			log.info("""
					==========================================
					RESPONSE SENT
					Status      : {}
					Time Taken  : {} ms
					==========================================
					""", response.getStatus(), totalTime);

			TenantContext.clear();
		}
	}

}