package com.prj.reservation.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prj.reservation.entity.Admin;
import com.prj.reservation.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID>{
    Optional<Client> findByUsername(String username);
}