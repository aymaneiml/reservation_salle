package com.prj.reservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prj.reservation.dto.ClientDTO;
import com.prj.reservation.dto.LoginRequestDTO;
import com.prj.reservation.exception.GlobalResponse;
import com.prj.reservation.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<GlobalResponse<String>> signup(
        @RequestBody  ClientDTO clientDTO
    ) {
        authService.signup(clientDTO);
        return new ResponseEntity<>(new GlobalResponse<>("Signed Up"), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<GlobalResponse<String>> login(
        @RequestBody LoginRequestDTO loginRequest
    ) {
        String token = authService.login(loginRequest);
        return new ResponseEntity<>(new GlobalResponse<>(token), HttpStatus.OK);
    }



}