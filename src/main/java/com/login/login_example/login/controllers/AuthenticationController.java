package com.login.login_example.login.controllers;

import com.login.login_example.commons.dto.response.CustomResponse;
import com.login.login_example.login.domain.Dto.RegistrationUserData;
import com.login.login_example.login.domain.Dto.UserAuthenticationData;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthenticationController {

    @PostMapping("/register-user")
    public ResponseEntity<CustomResponse> registerUser(@RequestBody @Valid RegistrationUserData registrationUserData);
    @PostMapping("/auth/singIn")
    public ResponseEntity<CustomResponse> authenticationUser(@RequestBody @Valid UserAuthenticationData userAuthenticationData);
}
