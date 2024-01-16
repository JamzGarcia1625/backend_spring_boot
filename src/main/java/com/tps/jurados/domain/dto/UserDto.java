package com.tps.jurados.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @JsonProperty("access_token")
    private TokenDto accessToken;
    @JsonProperty("refresh_token")
    private TokenDto refreshToken;
    private String username;
}
