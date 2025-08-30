package com.prj.reservation.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="users")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(name="username", unique = true, nullable = false, length = 100)
    private String username;
    
    @Column(name="password", nullable = false, length = 100)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role; //user, admin

}