package com.tps.jurados.web.controller;

import com.tps.jurados.domain.dto.request.AuthenticationRequest;
import com.tps.jurados.domain.dto.UserDto;
import com.tps.jurados.domain.exception.HttpGenericException;
import com.tps.jurados.domain.service.IAuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    @Autowired
    private IAuthService authService;

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody @Valid AuthenticationRequest authenticationRequest, HttpServletRequest request) throws HttpGenericException {
        try {
            UserDto response = authService.login(authenticationRequest);
            return ResponseEntity.ok(response);
        } catch (HttpGenericException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}
