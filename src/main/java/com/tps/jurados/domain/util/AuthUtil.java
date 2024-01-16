package com.tps.jurados.domain.util;

import com.tps.jurados.domain.dto.AuthCheckTokenDto;
import com.tps.jurados.domain.dto.AuthCheckTokenWithPermissionsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AuthUtil {

    public AuthCheckTokenWithPermissionsDto convertAuthCheckDto(List<AuthCheckTokenDto> checks) {
        AuthCheckTokenWithPermissionsDto authCheckTokenWithPermissionsDto = new AuthCheckTokenWithPermissionsDto();
        if (!checks.isEmpty()) {
            authCheckTokenWithPermissionsDto.setUsername(checks.get(0).getUsername());
            authCheckTokenWithPermissionsDto.setToken(checks.get(0).getToken());
            authCheckTokenWithPermissionsDto.setSingleSession(checks.get(0).isSingleSession());
            authCheckTokenWithPermissionsDto.setActions(checks.stream().map(AuthCheckTokenDto::getAction).collect(Collectors.toList()));
        }
        return authCheckTokenWithPermissionsDto;
    }
}
