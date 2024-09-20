package org.jaybon.jaylog.config.security.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jaybon.jaylog.common.constants.Constants;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final CustomUserDetailsService customUserDetailsService;

    public JwtAuthorizationFilter(
            CustomUserDetailsService customUserDetailsService
    ) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String accessJWTHeader = request.getHeader(Constants.Jwt.ACCESS_HEADER_NAME);
        DecodedJWT decodedAccessJWT;
        if (accessJWTHeader == null || !accessJWTHeader.startsWith(Constants.Jwt.HEADER_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        String accessJWT = accessJWTHeader.replace(Constants.Jwt.HEADER_PREFIX, "");
        try {
            decodedAccessJWT = JWT.require(Algorithm.HMAC512(Constants.Jwt.SECRET))
                    .build()
                    .verify(accessJWT);
        } catch (JWTVerificationException e) {
            chain.doFilter(request, response);
            return;
        }
        if (decodedAccessJWT.getExpiresAt().before(new java.util.Date())) {
            chain.doFilter(request, response);
            return;
        }
        CustomUserDetails customUserDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(decodedAccessJWT.getClaim("username").asString());
        if (customUserDetails.getUser().getJwtValidator() > decodedAccessJWT.getClaim("timestamp").asLong()) {
            chain.doFilter(request, response);
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        customUserDetails,
                        null,
                        customUserDetails.getAuthorities()
                )
        );
        chain.doFilter(request, response);
    }
}
