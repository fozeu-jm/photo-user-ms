package com.ffjm.photoapp.usermicroservice.service;

import com.ffjm.photoapp.usermicroservice.dto.UserDto;

public interface UserService {
    UserDto createUser(UserDto userDetails);
}
