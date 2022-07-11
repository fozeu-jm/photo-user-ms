package com.ffjm.photoapp.usermicroservice.controllers.model;

import com.ffjm.photoapp.usermicroservice.clients.model.PhotoResponseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserReponseModel {
    private String firstName;
    private String lastName;
    private String email;
    private String userId;
    private List<PhotoResponseModel> photoList;
}
