package com.denison.shops.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    // 생성자에서 Bean 주입
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, CustomAuthenticationEntryPoint customAuthenticationEntryPoint) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                    .antMatchers("/member/modify").authenticated()
                    .antMatchers("/", "/index.html", "/libs/**", "/img/**", "/login","/best/**","/search*").permitAll() // 정적 리소스
                    .antMatchers("/member/**").permitAll() // 로그인/회원가입만 허용
                    .antMatchers("/api/auth/login").permitAll() // 로그인만 허용
                    .antMatchers("/api/board/**", "/api/product/**").permitAll()          // 게시판 전체 허용
                    .antMatchers("/board/**").permitAll()
                    .antMatchers("/inc/**").permitAll()
                    .antMatchers("/product/**").permitAll()     // 상품 관련 전체 허용
                    // 나머지 회원 관련 URL은 인증 필요
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                    .authenticationEntryPoint(customAuthenticationEntryPoint)
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
