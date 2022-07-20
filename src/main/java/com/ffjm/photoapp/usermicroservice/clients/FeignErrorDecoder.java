package com.ffjm.photoapp.usermicroservice.clients;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class FeignErrorDecoder implements ErrorDecoder {
    Logger logger = LoggerFactory.getLogger(FeignErrorDecoder.class);

    @Override
    public Exception decode(String methodKey, Response response) {

        switch (response.status()) {
            case 400:
                return new ResponseStatusException(HttpStatus.valueOf(response.status()), "Bad request");
            case 404:
                String message = response.reason();
                if (methodKey.contains("getUsersPhoto")) {
                    message = "Users photo  not found";
                }
                logger.error(new ResponseStatusException(HttpStatus.valueOf(response.status()), message).getLocalizedMessage());
                return new ResponseStatusException(HttpStatus.valueOf(response.status()), message);
            default:
                return new Exception(response.reason());
        }
    }

}
