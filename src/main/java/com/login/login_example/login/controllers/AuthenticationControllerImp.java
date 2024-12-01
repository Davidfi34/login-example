package com.login.login_example.login.controllers;

import com.login.login_example.commons.controller.GenericRestController;
import com.login.login_example.commons.dto.response.CustomResponse;
import com.login.login_example.infra.security.JwtTokenData;
import com.login.login_example.infra.security.TokenService;
import com.login.login_example.login.domain.Dto.RegistrationUserData;
import com.login.login_example.login.domain.Dto.UserAuthenticationData;
import com.login.login_example.login.domain.User;
import com.login.login_example.login.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.login.login_example.commons.constants.GlobalApiConstant.AUTHENTICATED;
import static com.login.login_example.commons.constants.GlobalApiConstant.CREATED;
import static com.login.login_example.login.constants.LoginConstants.REQUEST_LOGIN;

@RestController
@RequestMapping(REQUEST_LOGIN)
public class AuthenticationControllerImp extends GenericRestController implements AuthenticationController{

    private AuthenticationManager authenticationManager;
    private TokenService tokenService;
    private UserService userService;


    public AuthenticationControllerImp(AuthenticationManager authenticationManager,TokenService tokenService, UserService userService){
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @Override
    public ResponseEntity<CustomResponse> registerUser(RegistrationUserData registrationUserData) {
        userService.registerUser(registrationUserData);
        return ok(null,CREATED,REQUEST_LOGIN);
    }

    @Override
    public ResponseEntity<CustomResponse> authenticationUser(UserAuthenticationData userAuthenticationData) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(userAuthenticationData.username(),
                userAuthenticationData.password());
        var authenticatedUser = authenticationManager.authenticate(authToken);
        var JwtToken = tokenService.generateToken((User) authenticatedUser.getPrincipal());
        return ok(new JwtTokenData(JwtToken),AUTHENTICATED,REQUEST_LOGIN);
    }

}
