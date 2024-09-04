package com.e.commerce.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;


@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final RedisTemplate<String, Object> redisTemplate;
    private final JwtService service;

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {

        Cookie cookie = WebUtils.getCookie(request, "refreshToken");
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        jwt = authHeader.substring(7);

        String s = service.extractUserId(jwt);
        String redisUserPage = "userPage::" + s;
        redisTemplate.delete(redisUserPage);

        if (cookie == null) {
            return;
        }
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        SecurityContextHolder.clearContext();
    }
}
