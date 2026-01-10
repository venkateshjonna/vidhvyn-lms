package com.venkatesh.vidhvyn.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth->auth
                        .requestMatchers(
                                "/",
                                "/user/register",
                                "/user/verify-email",
                                "/user/verification-success",
                                "/user/verification-failure",
                                "/user/resend-verification",
                                "/auth/login",
                                "/dashboard/main"

                        ).permitAll()
                        .anyRequest().authenticated()
                );
        return http.build();
    }

}
