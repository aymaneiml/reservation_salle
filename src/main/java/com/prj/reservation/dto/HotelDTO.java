package com.prj.reservation.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record HotelDTO(
    
    @NotNull(message ="name is required")
    @Size(min = 2, max = 50, message = "min is 2 characters and max is 50 characters")
    String name,

    @NotNull(message ="adress is required")
    @Size(min = 2, max = 50, message = "min is 2 characters and max is 50 characters")
    String adress,

    @NotNull(message ="stars is required")
    @Size(min = 2, max = 50, message = "min is 2 characters and max is 50 characters")
    String stars
) {
}