package com.denison.shops.security;


import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,HttpServletResponse response,AuthenticationException authException) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        String uri = request.getRequestURI();
        StringBuilder script = new StringBuilder("<script>alert('정상적인 경로로 접근이 되지 않았습니다');");
        // 회원 전용 페이지 접근 시에만 토큰 삭제
        if (uri.startsWith("/member") || uri.startsWith("/api/member")) {
            script.append("localStorage.removeItem('accessToken');");
        }
        script.append("location.href='/';</script>");

        response.getWriter().write(script.toString());
        response.getWriter().flush();
    }

}
