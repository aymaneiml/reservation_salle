package com.prj.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prj.reservation.entity.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long>{
}