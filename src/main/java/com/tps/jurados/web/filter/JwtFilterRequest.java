package com.tps.jurados.web.filter;

import com.tps.jurados.domain.dto.request.UserFilterRequestDto;
import com.tps.jurados.domain.service.IAuthService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtFilterRequest extends OncePerRequestFilter {

    @Autowired
    private IAuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
            String jwt = authorizationHeader.substring(7);
            UserFilterRequestDto userFilterRequestDto;
            UserDetails userDetails;
            try {
                userFilterRequestDto = authService.getUserDetails(jwt);
                userDetails = userFilterRequestDto.getUserDetailDto().getUserDetails();
            } catch (JwtException e) {
                userDetails = null;
                userFilterRequestDto = null;
            }

            if (userDetails != null && authService.validateToken(jwt, userFilterRequestDto)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                Map<String, Object> details = new HashMap<>();
                details.put("userId", userFilterRequestDto.getUserDetailDto().getUserId());
                details.put("ipRemote", request.getRemoteAddr());
                details.put("isEmulated", userFilterRequestDto.getUserDetailDto().isTokenEmulated());
                authToken.setDetails(details);
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                SecurityContextHolder.getContext().setAuthentication(null);
            }
        }

        Cookie cookie = new Cookie("procesoselectorales", "procesoselectorales");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);
        filterChain.doFilter(request, response);
    }
}
