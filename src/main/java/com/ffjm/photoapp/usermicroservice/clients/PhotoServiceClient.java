package com.ffjm.photoapp.usermicroservice.clients;

import com.ffjm.photoapp.usermicroservice.clients.model.PhotoResponseModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "photo-ms")
public interface PhotoServiceClient {

    @GetMapping("/photo/users/{userId}")
    List<PhotoResponseModel> getUsersPhoto(@PathVariable String userId);
}
