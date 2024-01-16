package com.tps.jurados.domain.repository;

import com.tps.jurados.domain.dto.AuthCheckTokenDto;

import java.util.List;

public interface IUserRepository {
    List<AuthCheckTokenDto> getAuthCheckToken(String username);
}
