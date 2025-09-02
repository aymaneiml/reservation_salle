package com.prj.reservation.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
    @Min(value = 1, message = "Minimum stars is 1")
    @Max(value = 5, message = "Maximum stars is 5")
    int stars
) {
}