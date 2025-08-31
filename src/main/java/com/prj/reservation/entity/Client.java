package com.prj.reservation.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
@DiscriminatorValue("CLIENT")
public class Client extends User{

    @Column(name = "location", nullable = false)
    private String location;
    @Column(name = "phone_number", length = 25)
    private String phoneNumber;

}