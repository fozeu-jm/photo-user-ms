package com.ffjm.photoapp.usermicroservice.controllers.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestModel {
    @NotNull(message = "first name cannot be null")
    @NotBlank(message = "first name cannot be blank")
    @Size(min = 2, message = "first name cannot be lass than two characters")
    private String firstName;

    @NotNull(message = "last name cannot be null")
    @NotBlank(message = "last name cannot be blank")
    @Size(min = 2, message = "last name cannot be lass than two characters")
    private String lastName;

    @NotNull(message = "password cannot be null")
    @NotBlank(message = "password cannot be blank")
    @Size(min = 8, message = "password should be at least 8 characters")
    private String passWord;

    @Email(message = "email is not valid")
    @NotNull(message = "email cannot be null")
    @NotBlank(message = "email cannot be blank")
    private String email;

}
