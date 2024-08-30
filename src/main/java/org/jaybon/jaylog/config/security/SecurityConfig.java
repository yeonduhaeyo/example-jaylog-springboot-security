package org.jaybon.jaylog.config.security;

import com.example.my.config.security.auth.CustomAuthenticationFailureHandler;
import com.example.my.config.security.auth.CustomAuthenticationSuccessHandler;
import com.example.my.config.security.auth.CustomLogoutSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
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

    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);

        httpSecurity.csrf(config -> config.disable());

        if ("dev" .equals(activeProfile)) {
            httpSecurity.headers(config -> config
                    .frameOptions(frameOptionsConfig -> frameOptionsConfig
                            .disable()
                    )
            );

//        httpSecurity.authorizeHttpRequests(config -> config
//                .requestMatchers(PathRequest.toH2Console())
//                .permitAll()
//        );

            httpSecurity.authorizeHttpRequests(config -> config
                    .requestMatchers(AntPathRequestMatcher.antMatcher("/h2/**"))
                    .hasRole("ADMIN")
            );
        }

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
                        mvcMatcherBuilder.pattern("/js/admin*.js"),
                        mvcMatcherBuilder.pattern("/temp/**")
                )
                .hasRole("ADMIN")
        );

        httpSecurity.authorizeHttpRequests(config -> config
                .requestMatchers(
                        mvcMatcherBuilder.pattern("/auth/**"),
                        mvcMatcherBuilder.pattern("/v*/auth/**"),
                        mvcMatcherBuilder.pattern("/docs/**"),
                        mvcMatcherBuilder.pattern("/swagger-ui/**")
                )
                .permitAll()
                .requestMatchers(
                        mvcMatcherBuilder.pattern("/admin/**"),
                        mvcMatcherBuilder.pattern("/v*/admin/**")
                )
                .hasRole("ADMIN")
                .anyRequest()
                .authenticated()
        );

        httpSecurity.formLogin(config -> config
                .loginPage("/auth/login")
                .loginProcessingUrl("/v*/auth/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(customAuthenticationSuccessHandler)
                .failureHandler(customAuthenticationFailureHandler)
                .permitAll()
        );

        httpSecurity.logout(config -> config
                .logoutUrl("/auth/logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler(customLogoutSuccessHandler)
                .permitAll()
        );

        return httpSecurity.getOrBuild();
    }

}
