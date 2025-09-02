package com.prj.reservation.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prj.reservation.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, UUID>{

    Optional<Admin> findByUsername(String username);
}