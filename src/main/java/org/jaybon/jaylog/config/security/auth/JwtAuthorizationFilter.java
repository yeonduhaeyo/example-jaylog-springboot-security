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
import org.jaybon.jaylog.common.exception.AuthenticationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    public JwtAuthorizationFilter(
            AuthenticationManager authenticationManager
    ) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String jwtHeader = request.getHeader(Constants.Jwt.HEADER_STRING);
        String jwtToken;
        DecodedJWT decodedJWT;
        if (jwtHeader == null || !jwtHeader.startsWith(Constants.Jwt.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        try {
            jwtToken = jwtHeader.replace(Constants.Jwt.TOKEN_PREFIX, "");
            decodedJWT = JWT.require(Algorithm.HMAC512(Constants.Jwt.SECRET))
                    .build()
                    .verify(jwtToken);
        } catch (JWTVerificationException e) {
            throw new AuthenticationException("토큰 검증 실패");
        }
        CustomUserDetails customUserDetails = new CustomUserDetails(
                CustomUserDetails.User.builder()
                        .id(decodedJWT.getClaim("id").asLong())
                        .username(decodedJWT.getClaim("username").asString())
                        .roleList(decodedJWT.getClaim("roleList").asList(String.class))
                        .build()
        );
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
