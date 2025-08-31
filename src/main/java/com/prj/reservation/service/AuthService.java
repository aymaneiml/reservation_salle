package com.prj.reservation.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.prj.reservation.dto.ClientDTO;
import com.prj.reservation.entity.User;
import com.prj.reservation.exception.CustomResponseException;
import com.prj.reservation.repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void signup(ClientDTO clientDTO) { 

        User newUser = new User();

        Optional<User> userExists = userRepository.findOneByUsername(clientDTO.username());
        if(userExists.isPresent()){
            throw CustomResponseException.AlreadyExists("user already existe with this username");
        }

        newUser.setUsername(clientDTO.username());
        newUser.setFirstName(clientDTO.firstName());
        newUser.setLastName(clientDTO.lastName());
        newUser.setEmail(clientDTO.email());
        newUser.setPassword(passwordEncoder.encode(clientDTO.password()));

        userRepository.save(newUser);
    }

}