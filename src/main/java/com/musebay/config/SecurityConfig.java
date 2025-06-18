package com.musebay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpMethod;

@Configuration
public class SecurityConfig {

    @PostConstruct
    public void init() {
        System.out.println("✅✅ SecurityConfig is ACTIVATED");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        System.out.println("🔥 SecurityFilterChain loaded");

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/", "/become", "/thankyou", "/admin", "/admin/**",
                                "/css/**", "/images/**", "/js/**"
                        )
                        .permitAll()
                        .requestMatchers(HttpMethod.POST, "/become").permitAll()
                        .anyRequest().permitAll()
                )
                .csrf(csrf -> csrf.disable()); // 폼 제출 테스트할 땐 잠깐 꺼도 OK

        return http.build();
    }
}
