package com.ffjm.photoapp.usermicroservice.configuration;

import com.ffjm.photoapp.usermicroservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;

@EnableWebSecurity
@Configuration
public class WebSecurity {
    private final Environment environment;
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public WebSecurity(Environment environment, BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService) {
        this.environment = environment;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
        //disable csrf on api
        http.cors().and()
                .csrf().disable()
                .authorizeRequests().antMatchers("/**").hasIpAddress(environment.getProperty("gateway.ip"))
                .and()
                .addFilter(getAuthFilter(authenticationManager))
                .authenticationManager(authenticationManager)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.headers().frameOptions().disable();
        return http.build();
    }

    //define custom login path
    public AuthFilter getAuthFilter(AuthenticationManager authenticationManager) {
        final AuthFilter filter = new AuthFilter(authenticationManager, userService, environment);
        filter.setFilterProcessesUrl("/api/auth");
        return filter;
    }

}
