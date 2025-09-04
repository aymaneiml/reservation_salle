package com.prj.reservation.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.prj.reservation.entity.ReservationStatus;

import lombok.AllArgsConstructor;


public record ReservationResponseDTO(
        Long reservationId,
        String clientFirstName,
        String clientLastName,
        String roomNumber,
        ReservationStatus status,
        LocalDate startDate,
        LocalDate endDate,
        String message
) {}