package com.tps.jurados.domain.dto.request;

import com.tps.jurados.domain.dto.AuthCheckTokenWithPermissionsDto;
import com.tps.jurados.domain.dto.UserDetailDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserFilterRequestDto {

    private UserDetailDto userDetailDto;

    private AuthCheckTokenWithPermissionsDto authCheckTokenWithPermissionsDto;
}
