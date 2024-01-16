package com.tps.jurados.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailDto {

    UserDetails userDetails;

    int UserId;

    boolean tokenEmulated;

    @Override
    public String toString() {
        return "UserDetailDto{" +
                "userDetails=" + userDetails +
                ", UserId=" + UserId +
                ", tokenEmulated=" + tokenEmulated +
                '}';
    }
}
