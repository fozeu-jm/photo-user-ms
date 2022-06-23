package com.ffjm.photoapp.usermicroservice.controllers;

import com.ffjm.photoapp.usermicroservice.controllers.model.UserReponseModel;
import com.ffjm.photoapp.usermicroservice.controllers.model.UserRequestModel;
import com.ffjm.photoapp.usermicroservice.dto.UserDto;
import com.ffjm.photoapp.usermicroservice.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/status/check")
    public String status(){
        return "Working !!";
    }

    @PostMapping
    public ResponseEntity<UserReponseModel> creatUser(@Valid @RequestBody UserRequestModel userRequestModel){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = modelMapper.map(userRequestModel, UserDto.class);
        UserReponseModel userReponseModel = modelMapper.map(userService.createUser(userDto), UserReponseModel.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(userReponseModel);
    }
}
