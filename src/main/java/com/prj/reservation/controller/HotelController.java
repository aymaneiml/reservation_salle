package com.prj.reservation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prj.reservation.dto.HotelDTO;
import com.prj.reservation.dto.RoomDTO;
import com.prj.reservation.entity.Hotel;
import com.prj.reservation.entity.Room;
import com.prj.reservation.exception.GlobalResponse;
import com.prj.reservation.service.HotelService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @GetMapping
    public ResponseEntity<GlobalResponse<List<Hotel>>> findAllHotels(){
        List<Hotel> hotels = hotelService.getAllHotels();
        return new ResponseEntity<>(new GlobalResponse<>(hotels), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GlobalResponse<Hotel>> addHotel(@RequestBody @Valid HotelDTO newHotel) {
        Hotel hotel = hotelService.CreateHotel(newHotel);
        return new ResponseEntity<>(new GlobalResponse<>(hotel), HttpStatus.CREATED);
    }

    @PutMapping("/{hotelId}")
    public ResponseEntity<GlobalResponse<Hotel>> updateHotel(@PathVariable Long hotelId, @RequestBody @Valid HotelDTO hotelDTO){
        Hotel updatedHotel = hotelService.updateHotel(hotelDTO, hotelId);
        return new ResponseEntity<>(new GlobalResponse<>(updatedHotel), HttpStatus.OK);
    }

    @DeleteMapping("/{hotelId}")
    public ResponseEntity<Void> deleteHotel(@PathVariable Long hotelId){
        hotelService.deleteHotelById(hotelId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<GlobalResponse<Hotel>> getHotelById(@PathVariable Long hotelId) {
        Hotel hotel = hotelService.getHotelById(hotelId);
        return new ResponseEntity<>(new GlobalResponse<>(hotel), HttpStatus.OK);
    }


}