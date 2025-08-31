package com.prj.reservation.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

public record ReservationDTO(

    @NotNull(message = " client id is required")
    UUID clientId,

    @NotNull(message = "room id is required")
    Long roomId,

    @NotNull(message = "start Date is required")
    @PastOrPresent(message = "start date should be now or in the future")
    LocalDateTime startTime,

    @NotNull(message = "End Date is required")
    @PastOrPresent(message = "end date cannot be inthe futur")
    LocalDateTime endTime
) {

}