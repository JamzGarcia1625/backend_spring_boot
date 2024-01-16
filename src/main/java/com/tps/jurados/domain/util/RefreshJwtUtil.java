package com.tps.jurados.domain.util;

import com.tps.jurados.domain.dto.request.AuthenticationRequest;
import com.tps.jurados.domain.dto.TokenDto;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class RefreshJwtUtil extends JwtParentUtil {
    private static final int JWT_TOKEN_VALIDITY = 70 * 60;

    @Value("${jwt.secret.refresh-token}")
    private String secret;

    public TokenDto generateToken(AuthenticationRequest userDetails, boolean isEmulated) {
        return new TokenDto(doGenerateToken(userDetails.getUsername(), addEmulatedClaim(new HashMap<String, Object>(), isEmulated), JWT_TOKEN_VALIDITY, secret), JWT_TOKEN_VALIDITY * 1000L);
    }

    public Claims getClaims(String token) {
        return getClaims(token, secret);
    }
}
