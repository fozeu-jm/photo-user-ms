package com.ffjm.photoapp.usermicroservice.controllers.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserReponseModel {
    private String firstName;
    private String lastName;
    private String email;
    private String userId;
}
