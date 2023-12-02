package com.tech.bb.moviesservice.config;

import com.tech.bb.moviesservice.entity.Users;
import com.tech.bb.moviesservice.service.AuthenticationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@Slf4j
public class AuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationService authenticationService;
    private static final ThreadLocal<Optional<Users>> securityContext = new ThreadLocal<>();

    public AuthenticationFilter(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public static Optional<Users> getSecurityContext() {
        return securityContext.get();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String apiKey = request.getHeader("apiKey");
        log.info("AUTHENTICATION SERVICE ::: Authenticating User via the API Key - {}", apiKey);
        securityContext.set(authenticationService.authenticate(apiKey));
        filterChain.doFilter(request, response);
        securityContext.remove();
    }
}
