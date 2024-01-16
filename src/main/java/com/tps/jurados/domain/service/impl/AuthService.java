package com.tps.jurados.domain.service.impl;

import com.tps.jurados.domain.dto.*;
import com.tps.jurados.domain.dto.request.AuthenticationRequest;
import com.tps.jurados.domain.dto.request.UserFilterRequestDto;
import com.tps.jurados.domain.repository.IUserRepository;
import com.tps.jurados.domain.service.IAuthService;
import com.tps.jurados.domain.util.AuthUtil;
import com.tps.jurados.domain.util.JwtUtil;
import com.tps.jurados.domain.util.RefreshJwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService implements IAuthService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthUtil authUtil;

    @Autowired
    private RefreshJwtUtil refreshJwtUtil;

    @Autowired
    private IUserRepository userRepository;

    @Override
    public UserDto login(AuthenticationRequest authenticationRequest) {
        RefreshTokenDto refreshToken = generateTokens(authenticationRequest);
        return new UserDto(refreshToken.getAccessToken(), refreshToken.getRefreshToken(),
                authenticationRequest.getUsername());
    }

    private RefreshTokenDto generateTokens(AuthenticationRequest args) {
        
        TokenDto accessToken = jwtUtil.generateToken(args);
        TokenDto refreshToken = refreshJwtUtil.generateToken(args, false);
        return new RefreshTokenDto(accessToken, refreshToken);
    }

    @Override
    public UserFilterRequestDto getUserDetails(String token) {
        Claims claims = jwtUtil.getClaims(token);
        AuthCheckTokenWithPermissionsDto authCheckTokenWithPermissionsDto = authUtil.convertAuthCheckDto(userRepository.getAuthCheckToken(jwtUtil.getSubject(claims)));
        List<SimpleGrantedAuthority> permissions = authCheckTokenWithPermissionsDto.getActions().stream().map(SimpleGrantedAuthority::new).toList();
        UserDetailDto userDetailDto = jwtUtil.getUserDetails(token, permissions);
        return new UserFilterRequestDto(userDetailDto, authCheckTokenWithPermissionsDto);
    }

    @Override
    public boolean validateToken(String token, UserFilterRequestDto userFilterRequestDto) {
        return userFilterRequestDto.getAuthCheckTokenWithPermissionsDto().isPresent() && jwtUtil.validateToken(token, userFilterRequestDto.getUserDetailDto().getUserDetails(), userFilterRequestDto.getAuthCheckTokenWithPermissionsDto());
    }
}
