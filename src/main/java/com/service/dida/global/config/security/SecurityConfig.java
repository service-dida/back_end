package com.service.dida.global.config.security;

import com.service.dida.global.config.security.jwt.JwtAuthenticationFilter;
import com.service.dida.global.config.security.jwt.JwtExceptionFilter;
import com.service.dida.global.config.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                UsernamePasswordAuthenticationFilter.class)
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .formLogin().disable()
            .httpBasic().disable()
            .authorizeHttpRequests()
            .requestMatchers("/user/**").hasAnyRole("USER", "MANAGER")
            .requestMatchers("/manager/**").hasAnyRole("MANAGER")
            .anyRequest().permitAll()
            .and()
            .addFilterBefore(new JwtExceptionFilter(),
                JwtAuthenticationFilter.class)
        ;

        return http.build();
    }
}
