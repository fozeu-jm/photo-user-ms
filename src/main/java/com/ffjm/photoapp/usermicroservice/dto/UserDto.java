package com.ffjm.photoapp.usermicroservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {

    private static final long serialVersionUID = 8409967843217741819L;

    private String firstName;

    private String lastName;

    @JsonIgnore
    private String passWord;

    private String email;

    @JsonIgnore
    private String encryptedPassword;

    private String userId;
}
