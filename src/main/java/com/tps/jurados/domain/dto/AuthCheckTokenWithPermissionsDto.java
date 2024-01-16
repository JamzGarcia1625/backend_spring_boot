package com.tps.jurados.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthCheckTokenWithPermissionsDto {

    private String username;
    private String token;
    private boolean singleSession;
    private List<String> actions;

    public boolean isPresent() {
        return !Objects.isNull(username);
    }
}
