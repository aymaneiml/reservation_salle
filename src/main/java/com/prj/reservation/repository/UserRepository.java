package com.prj.reservation.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prj.reservation.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>{

    //Optional<User> findOneByAccountCreationToken(String token);
    Optional<User> findOneByUsername(String username);
}