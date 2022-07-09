package com.ffjm.photoapp.usermicroservice.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ffjm.photoapp.usermicroservice.controllers.model.LoginRequestModel;
import com.ffjm.photoapp.usermicroservice.dto.UserDto;
import com.ffjm.photoapp.usermicroservice.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;


public class AuthFilter extends UsernamePasswordAuthenticationFilter {
    private final UserService userService;
    private final Environment environment;

    public AuthFilter(AuthenticationManager authenticationManager, UserService userService, Environment environment) {
        super(authenticationManager);
        this.userService = userService;
        this.environment = environment;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) {
        try {
            LoginRequestModel creds = new ObjectMapper().readValue(req.getInputStream(), LoginRequestModel.class);
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getEmail(),
                            creds.getPassword(),
                            new ArrayList<>()
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res,
                                            FilterChain chain, Authentication authResult) {
        String userName = ((User) authResult.getPrincipal()).getUsername();
        UserDto userdetails = userService.getUserByEmail(userName);

        String token = Jwts.builder()
                .setSubject(userdetails.getUserId())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(Objects.requireNonNull(environment.getProperty("token.expiration_delay")))))
                .signWith(SignatureAlgorithm.HS512, environment.getProperty("token.secret"))
                .compact();
        res.addHeader("token", token);
        res.addHeader("token_expiration", environment.getProperty("token.expiration_delay"));
        res.addHeader("userId", userdetails.getUserId());
    }


}
