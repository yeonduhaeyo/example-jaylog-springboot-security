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
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

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
