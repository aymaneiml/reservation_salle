package com.prj.reservation.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.prj.reservation.entity.ReservationStatus;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

public record ReservationDTO(

    @NotNull(message = "room id is required")
    Long roomId,

    @NotNull(message = "start Date is required")
    @FutureOrPresent(message = "Start date should be now or in the future")
    LocalDate startDate,

    @NotNull(message = "End Date is required")
    @FutureOrPresent(message = "End date cannot be in the past")
    LocalDate endDate


) {

}