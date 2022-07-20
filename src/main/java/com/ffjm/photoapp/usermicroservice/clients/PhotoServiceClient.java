package com.ffjm.photoapp.usermicroservice.clients;

import com.ffjm.photoapp.usermicroservice.clients.model.PhotoResponseModel;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name = "photo-ms")
public interface PhotoServiceClient {

    @GetMapping("/photo/userss/{userId}")
    @CircuitBreaker(name = "user-ms", fallbackMethod = "getPhotoFallback")
    List<PhotoResponseModel> getUsersPhoto(@PathVariable String userId);

    default List<PhotoResponseModel> getPhotoFallback(String userId, Throwable exc) {
        return new ArrayList<>();
    }
}
