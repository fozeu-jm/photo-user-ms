package com.ffjm.photoapp.usermicroservice.controllers;

import com.ffjm.photoapp.usermicroservice.controllers.model.UserReponseModel;
import com.ffjm.photoapp.usermicroservice.controllers.model.UserRequestModel;
import com.ffjm.photoapp.usermicroservice.dto.UserDto;
import com.ffjm.photoapp.usermicroservice.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    private final UserService userService;
    private final Environment env;

    @Autowired
    public UsersController(UserService userService, Environment env) {
        this.userService = userService;
        this.env = env;
    }

    @RequestMapping("/status/check")
    public String status() {
        return String.format("Working on port %s", env.getProperty("local.server.port"));
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<UserReponseModel> creatUser(@Valid @RequestBody UserRequestModel userRequestModel) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = modelMapper.map(userRequestModel, UserDto.class);
        UserReponseModel userReponseModel = modelMapper.map(userService.createUser(userDto), UserReponseModel.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(userReponseModel);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserReponseModel> getUserById(@PathVariable String userId) {
        UserDto userDto = userService.getUserById(userId);
        return ResponseEntity.ok().body(new ModelMapper().map(userDto, UserReponseModel.class));
    }
}
