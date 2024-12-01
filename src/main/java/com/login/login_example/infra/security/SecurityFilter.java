package com.login.login_example.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.login.login_example.login.repository.UsersRepository;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {


    private TokenService tokenService;
    private UsersRepository usersRepository;

    public SecurityFilter(TokenService tokenService, UsersRepository usersRepository){
        this.tokenService = tokenService;
        this.usersRepository = usersRepository;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // get header token
        var authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            var token = authHeader.replace("Bearer ", "");
            var username = tokenService.getSubject(token); // extract username
            if (username != null) {
                // Token valid
                var user = usersRepository.findByUsername(username);
                var authentication = new UsernamePasswordAuthenticationToken(user, null,
                        user.getAuthorities()); //we force session
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);

    }
}
