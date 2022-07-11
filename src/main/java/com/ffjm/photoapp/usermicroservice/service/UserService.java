package com.ffjm.photoapp.usermicroservice.service;

import com.ffjm.photoapp.usermicroservice.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDetails);
    UserDto getUserByEmail(String email);
    UserDto getUserById(String userId);
}
