package com.prj.reservation.dto;

import java.util.UUID;

import com.prj.reservation.entity.RoomStatus;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RoomDTO(

    @NotNull(message = "room number is required")
    @Size(min = 2, max = 50, message = "min is 2 character and max is 50 character")
    String roomNumber,

    @NotNull(message = "capacity is required")
    int capacity,

    @NotNull(message = "description is required")
    @Size(min = 2, max = 50, message = "min is 2 character and max is 50 character")
    String description,

    @NotNull(message = "room status is required")
    RoomStatus roomStatus,

    @NotNull(message = "hotel id is required")
    Long hotelId,

    @NotNull(message = "Admin id is required")
    UUID adminId
) {
}