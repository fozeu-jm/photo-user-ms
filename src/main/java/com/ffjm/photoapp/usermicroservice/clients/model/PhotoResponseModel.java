package com.ffjm.photoapp.usermicroservice.clients.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhotoResponseModel {
    private String name;
    private String description;
    private String url;
    private String publicId;
    private String userId;
}
