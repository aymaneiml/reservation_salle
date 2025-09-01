package com.prj.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prj.reservation.entity.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long>{
}