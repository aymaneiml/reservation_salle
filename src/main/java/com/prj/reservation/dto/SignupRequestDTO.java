package com.prj.reservation.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SignupRequestDTO(

    @NotNull(message = "firstname is required")
    @Size(min = 2, max = 50, message = "min is 2 character")
    String firstName,

    @NotNull(message = "lastname is required")
    @Size(min = 2, max = 50, message = "min is 2 character")
    String lastName,

    @NotNull(message = "username is required")
    @Size(min = 2, max = 50, message = "min is 2 character")
    String username,

    @NotNull(message = "username is required")
    @Email(message = "Invalid email format")
    String email,

    String password
) {
}