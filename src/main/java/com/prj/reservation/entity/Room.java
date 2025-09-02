package com.prj.reservation.entity;


import java.util.UUID;

import org.hibernate.annotations.ManyToAny;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="room_number", unique = true, nullable = false)
    private String roomNumber;

    @Column(name="capacity", nullable = false)
    private int capacity;

    @Column(name="description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "room_price", nullable = false)
    private double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_status", nullable = false)
    private RoomStatus roomStatus;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="hotel_id", nullable = false)
    @JsonProperty("hotelId")
    private Hotel hotel;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="admin_id", nullable = false)
    @JsonProperty("adminId")
    private Admin admin;

    

    public Long getHotel(){
        return hotel.getId();
    }

    public UUID getAdmin(){
        return admin.getId();
    }



}

