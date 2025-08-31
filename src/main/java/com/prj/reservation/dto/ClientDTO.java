package com.prj.reservation.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ClientDTO(

    @NotNull(message = "firstname is required")
    @Size(min = 2, max = 50, message = "min is 2 character")
    String firstName,

    @NotNull(message = "lastname is required")
    @Size(min = 2, max = 50, message = "min is 2 character")
    String lastName,

    @NotNull(message = "phoneNumber is required")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$" , message= "Invalid phone number format")
    String phoneNumber,

    @NotNull(message = "locarion is required")
    @Size(min = 2, max = 50, message = "min is 2 character")
    String location,

    @NotNull(message = "username is required")
    @Size(min = 2, max = 50, message = "min is 2 character")
    String username,

    @NotNull(message = "username is required")
    @Email(message = "Invalid email format")
    String email,

    @NotNull(message ="password is required")
    @Size(min = 2, max = 50, message = "min is 2 characters and max is 50 characters")
    String password
) {
}