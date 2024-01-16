package com.tps.jurados.domain.service;

import com.tps.jurados.domain.dto.request.AuthenticationRequest;
import com.tps.jurados.domain.dto.UserDto;
import com.tps.jurados.domain.dto.request.UserFilterRequestDto;

public interface IAuthService {
    UserDto login(AuthenticationRequest authenticationRequest);
    UserFilterRequestDto getUserDetails(String token);
    boolean validateToken(String token, UserFilterRequestDto userFilterRequestDto);
}
