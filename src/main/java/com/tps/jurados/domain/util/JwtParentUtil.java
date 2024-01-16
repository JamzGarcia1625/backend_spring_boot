package com.tps.jurados.domain.util;

import com.tps.jurados.domain.dto.request.AuthenticationRequest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

abstract class JwtParentUtil {
    private static final String AUTHORITIES = "authorities";

    private static final String USER_ID = "userId";

    private static final String USERNAME = "username";

    private static final String EMULATED_ID = "emulatedId";

    protected String doGenerateToken(String username, Map<String, Object> claims, int time, String secret) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + time * 1000L))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    protected Map<String, Object> generateClaims(AuthenticationRequest userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(USERNAME, userDetails.getUsername());
        claims.put(USER_ID, 1);
        claims.put("role", "admin");

        return claims;
    }

    protected Map<String, Object> addEmulatedClaim(Map<String, Object> claims, boolean isEmulated) {
        claims.put(EMULATED_ID, isEmulated ? 1 : 0);
        return claims;
    }

    protected Claims getClaims(String token, String secret) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            claims = e.getClaims();
        }
        return claims;
    }

    public String getSubject(Claims claims) {
        return claims.getSubject();
    }

    protected int getUserId(Claims claims) {
        return claims.get(USER_ID, Integer.class);
    }

    public boolean isTokenEmulated(Claims claims) {
        if (claims.containsKey(EMULATED_ID)) return (claims.get(EMULATED_ID, Integer.class) != 0);
        return false;
    }

    public boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }
}
