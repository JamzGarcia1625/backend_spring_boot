package com.tps.jurados.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {
    @NotEmpty(message = "El usuario no puede ser nulo")
    @Size(max = 20, message = "El usuario debe tener máximo 20 caracteres")
    private String username;

    @NotEmpty(message = "La contraseña no puede ser nula")
    @Size(max = 20, message = "La contraseña debe tener máximo 20 caracteres")
    private String password;

    @JsonProperty("recaptcha_token")
    @NotEmpty(message = "El token captcha no puede ser nulo")
    private String recaptchaToken;
}
