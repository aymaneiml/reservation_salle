package com.prj.reservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prj.reservation.dto.ClientDTO;
import com.prj.reservation.exception.GlobalResponse;
import com.prj.reservation.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    public ResponseEntity<GlobalResponse<String>> signup(
        @RequestBody ClientDTO signupRequestDTO
    ) {
        authService.signup(signupRequestDTO);
        return new ResponseEntity<>(new GlobalResponse<>("Signed Up"), HttpStatus.OK);
    }

}