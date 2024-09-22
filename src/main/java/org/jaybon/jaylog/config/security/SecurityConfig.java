package org.jaybon.jaylog.config.security;

import lombok.RequiredArgsConstructor;
import org.jaybon.jaylog.config.security.auth.CustomAccessDeniedHandler;
import org.jaybon.jaylog.config.security.auth.CustomAuthenticationEntryPoint;
import org.jaybon.jaylog.config.security.auth.CustomUserDetailsService;
import org.jaybon.jaylog.config.security.auth.JwtAuthorizationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.lang.Nullable;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Nullable
    @Value("${spring.profiles.active}")
    String activeProfile;

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedHeaders(Collections.singletonList("*"));
            config.setAllowedMethods(Collections.singletonList("*"));
            config.setAllowedOriginPatterns(List.of(
                    "http://127.0.0.1:80",
                    "http://127.0.0.1:3000",
                    "http://127.0.0.1:3001",
                    "http://127.0.0.1:3002",
                    "http://127.0.0.1:5173",
                    "http://127.0.0.1:5174",
                    "http://127.0.0.1:5175",
                    "http://127.0.0.1:5500",
                    "http://127.0.0.1:5501",
                    "http://127.0.0.1:5502",
                    "http://localhost:80",
                    "http://localhost:3000",
                    "http://localhost:3001",
                    "http://localhost:3002",
                    "http://localhost:5173",
                    "http://localhost:5174",
                    "http://localhost:5175",
                    "http://localhost:5500",
                    "http://localhost:5501",
                    "http://localhost:5502"
            ));
            config.setAllowCredentials(true);
            return config;
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);

        if ("dev".equals(activeProfile)) {
            httpSecurity.authorizeHttpRequests(config -> config
                    .requestMatchers(AntPathRequestMatcher.antMatcher("/h2/**"))
                    .permitAll()
            );
        } else {
            httpSecurity.authorizeHttpRequests(config -> config
                    .requestMatchers(AntPathRequestMatcher.antMatcher("/h2/**"))
                    .hasRole("ADMIN")
            );
        }

        httpSecurity.headers(config -> config.frameOptions(frameOptionsConfig -> frameOptionsConfig.disable()));

        httpSecurity.cors(config -> config.configurationSource(corsConfigurationSource()));

        httpSecurity.csrf(config -> config.disable());

        httpSecurity.formLogin(config -> config.disable());

        httpSecurity.httpBasic(config -> config.disable());

        httpSecurity.sessionManagement(config -> config
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        httpSecurity.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

        httpSecurity.exceptionHandling(config -> config
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .accessDeniedHandler(customAccessDeniedHandler)
        );

        httpSecurity.authorizeHttpRequests(config -> config
                .requestMatchers(
                        mvcMatcherBuilder.pattern("/css/**"),
                        mvcMatcherBuilder.pattern("/js/**"),
                        mvcMatcherBuilder.pattern("/assets/**"),
                        mvcMatcherBuilder.pattern("/springdoc/**"),
                        mvcMatcherBuilder.pattern("/favicon.ico")
                )
                .permitAll()
                .requestMatchers(
                        mvcMatcherBuilder.pattern("/js/admin*.js")
                )
                .hasRole("ADMIN")
        );

        httpSecurity.authorizeHttpRequests(config -> config
                .requestMatchers(
                        mvcMatcherBuilder.pattern("/docs/**"),
                        mvcMatcherBuilder.pattern("/swagger-ui/**"),
                        mvcMatcherBuilder.pattern("/v*/auth/**"),
                        mvcMatcherBuilder.pattern("/v*/main/**"),
                        mvcMatcherBuilder.pattern(HttpMethod.GET, "/v*/article/**")
                )
                .permitAll()
                .requestMatchers(
                        mvcMatcherBuilder.pattern("/v*/admin/**")
                )
                .hasRole("ADMIN")
                .anyRequest()
                .authenticated()
        );

        return httpSecurity.getOrBuild();
    }

}
