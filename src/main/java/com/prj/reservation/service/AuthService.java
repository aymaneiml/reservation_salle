package com.prj.reservation.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.prj.reservation.dto.ClientDTO;
import com.prj.reservation.dto.LoginRequestDTO;
import com.prj.reservation.entity.Client;
import com.prj.reservation.entity.Role;
import com.prj.reservation.entity.User;
import com.prj.reservation.exception.CustomResponseException;
import com.prj.reservation.repository.UserRepository;
import com.prj.reservation.security.JwtUtil;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    public void signup(ClientDTO clientDTO) { 

        Client newUser = new Client();

        Optional<User> userExists = userRepository.findOneByUsername(clientDTO.username());
        if(userExists.isPresent()){
            throw CustomResponseException.AlreadyExists("user already existe with this username");
        }

        newUser.setUsername(clientDTO.username());
        newUser.setFirstName(clientDTO.firstName());
        newUser.setLastName(clientDTO.lastName());
        newUser.setEmail(clientDTO.email());
        newUser.setLocation(clientDTO.location());
        newUser.setPhoneNumber(clientDTO.phoneNumber());
        newUser.setRole(Role.CLIENT);
        newUser.setPassword(passwordEncoder.encode(clientDTO.password()));

        userRepository.save(newUser);
    }

    //login: retourn le Token
    public String login(LoginRequestDTO loginRequest) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.username(),
                loginRequest.password()
            )
        );

        User user = userRepository.findOneByUsername(loginRequest.username())
            .orElseThrow(() -> CustomResponseException.BadCredentials());

        Map<String, Object> customClaims = new HashMap<>();
        //dans cette map on peut ajouter tous les info qu'on veut
        customClaims.put("userId", user.getId());
        customClaims.put("username", user.getUsername());
        customClaims.put("role", user.getRole());

        return jwtUtil.generateToken(customClaims, user);
    }
    

}