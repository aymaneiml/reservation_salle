package com.prj.reservation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prj.reservation.dto.ReservationDTO;
import com.prj.reservation.dto.ReservationResponseDTO;
import com.prj.reservation.entity.Reservation;
import com.prj.reservation.exception.GlobalResponse;
import com.prj.reservation.service.ReservationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping
    public ResponseEntity<GlobalResponse<ReservationResponseDTO>> createReservation(@RequestBody @Valid ReservationDTO reservationDTO) {
        ReservationResponseDTO reservation = reservationService.createReservation(reservationDTO);
        return new ResponseEntity<>(new GlobalResponse<>(reservation),HttpStatus.CREATED);
    }

    @GetMapping("/me")
    public ResponseEntity<GlobalResponse<List<ReservationResponseDTO>>> getMyReservation(){
        List<ReservationResponseDTO> reservations = reservationService.getMyReservations();
        return new ResponseEntity<>(new GlobalResponse<>(reservations), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<GlobalResponse<List<ReservationResponseDTO>>> getAllReservation(){
        List<ReservationResponseDTO> reservations = reservationService.getAllReservations();
        return new ResponseEntity<>(new GlobalResponse<>(reservations), HttpStatus.OK);
    }

    @PutMapping("/{id}/confirm")
    public ResponseEntity<GlobalResponse<String>> confirmReservation(@PathVariable Long id) {
        reservationService.confirmedReservation(id);
        return new ResponseEntity<>(
                new GlobalResponse<>("La réservation a été confirmée avec succès !"),
                HttpStatus.OK
        );
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<GlobalResponse<String>> cancelReservation(@PathVariable Long id) {
        reservationService.cancelledReservation(id);
        return new ResponseEntity<>(
                new GlobalResponse<>("La réservation a été annuler avec succès !"),
                HttpStatus.OK
        );
    }


}