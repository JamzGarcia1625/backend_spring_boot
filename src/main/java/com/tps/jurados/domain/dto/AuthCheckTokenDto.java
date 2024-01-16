package com.tps.jurados.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthCheckTokenDto {

    private String username;
    private String token;
    private boolean singleSession;

    private String action;
}
