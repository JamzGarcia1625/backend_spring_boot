package com.tps.jurados.domain.util;

import com.tps.jurados.domain.dto.AuthCheckTokenWithPermissionsDto;
import com.tps.jurados.domain.dto.UserDetailDto;
import com.tps.jurados.domain.dto.request.AuthenticationRequest;
import com.tps.jurados.domain.dto.TokenDto;
import com.tps.jurados.domain.exception.HttpGenericException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JwtUtil extends JwtParentUtil {
    private static final int JWT_TOKEN_VALIDITY = 60 * 60;

    @Value("${jwt.secret.token}")
    private String secret;

    public TokenDto generateToken(AuthenticationRequest args) {
        return new TokenDto(doGenerateToken(args.getUsername(), addEmulatedClaim(generateClaims(args), false) , JWT_TOKEN_VALIDITY, secret), JWT_TOKEN_VALIDITY * 1000L);
    }

    public Claims getClaims(String token) {
        return getClaims(token, secret);
    }

    public UserDetailDto getUserDetails(String token, List<SimpleGrantedAuthority> permissions) throws HttpGenericException {
        Claims claims = getClaims(token);
        return new UserDetailDto(new User(getSubject(claims), "", permissions), getUserId(claims), isTokenEmulated(claims));
    }

    public boolean validateToken(String token, UserDetails userDetails, AuthCheckTokenWithPermissionsDto authCheckTokenDto) {
        Claims claims = getClaims(token);
        String username = getSubject(claims);
        return userDetails.getUsername().equals(username) && !isTokenExpired(claims) && (isTokenEmulated(claims) || validateCurrentToken(authCheckTokenDto, token));
    }

    public boolean validateCurrentToken(AuthCheckTokenWithPermissionsDto authCheckTokenDto, String token) {
        return !authCheckTokenDto.isSingleSession() || (authCheckTokenDto.getToken() != null && !authCheckTokenDto.getToken().isBlank() && authCheckTokenDto.getToken().equals(token));
    }
}
