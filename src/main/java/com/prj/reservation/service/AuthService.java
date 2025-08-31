package com.prj.reservation.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.prj.reservation.dto.SignupRequestDTO;
import com.prj.reservation.entity.User;
import com.prj.reservation.exception.CustomResponseException;
import com.prj.reservation.repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void signup(SignupRequestDTO signupRequestDTO) { 

        User newUser = new User();

        Optional<User> userExists = userRepository.findOneByUsername(signupRequestDTO.username());
        if(userExists.isPresent()){
            throw CustomResponseException.AlreadyExists("user already existe with this username");
        }

        newUser.setUsername(signupRequestDTO.username());
        newUser.setFirstName(signupRequestDTO.firstName());
        newUser.setLastName(signupRequestDTO.lastName());
        newUser.setEmail(signupRequestDTO.email());
        newUser.setPassword(passwordEncoder.encode(signupRequestDTO.password()));

        userRepository.save(newUser);
    }

}