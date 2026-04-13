package com.denison.shops.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    public JwtAuthenticationFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String token = jwtProvider.resolveToken(request);
       // System.out.println("체크 시작 : " + jwtProvider.validateToken(token));
        if (token != null) {
            if (jwtProvider.validateToken(token)) {
                String userid = jwtProvider.getUsername(token);
                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(userid, null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                SecurityContextHolder.clearContext();
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT 토큰 만료 또는 검증 실패");
                return; // 더 이상 체인 진행하지 않음
            }
        }
        filterChain.doFilter(request, response);
    }
}