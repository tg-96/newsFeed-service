package com.newsfeed.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // REST API 이므로 basic auth 및 csrf 보안을 사용하지 않는다.
                .httpBasic(basic -> basic.disable())
                .csrf(csrf -> csrf.disable())
                // JWT를 사용하기 때문에 세션을 사용하지 않음
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeRequests()
                // 해당 API에 대해서는 모든 요청을 허가
                .requestMatchers("/members/login").permitAll()
                // USER 권한이 있어야 요청 가능
//                .requestMatchers("/members/test").hasRole("USER")
                //다른 모든 요청에 관해 인증 필요
                .anyRequest().authenticated()
                .and()
                // JWT 인증을 위하여 직접 구현한 필터를 UsernamePasswordAuthenticationFilter 전에 실행
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class)
                .getOrBuild();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        //BCrypt Encoder 사용
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
