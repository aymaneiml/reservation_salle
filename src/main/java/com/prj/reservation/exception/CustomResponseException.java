package com.prj.reservation.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomResponseException extends RuntimeException{

    private int statusCode;
    private String message;

    public static CustomResponseException AlreadyExists(String message) {
        return new CustomResponseException(409, message);
    }

    public static CustomResponseException BadCredentials() {
        return new CustomResponseException(404, "Bad Credentials");
    }

    public static CustomResponseException BadRequest(String message) {
        return new CustomResponseException(400, message);
    }

    public static CustomResponseException ResourceNotFound(String message){
        return new CustomResponseException(404, message);
    }
}