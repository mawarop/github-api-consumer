package com.gmail.oprawam.githubapiconsumer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class GeneralResponseException extends RuntimeException{
    public GeneralResponseException(String message) {
        super(message);
    }
}
