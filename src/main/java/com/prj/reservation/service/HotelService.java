package com.prj.reservation.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prj.reservation.dto.HotelDTO;
import com.prj.reservation.entity.Hotel;
import com.prj.reservation.exception.CustomResponseException;
import com.prj.reservation.repository.HotelRepository;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    //get all hotels
    public List<Hotel> getAllHotels(){
        return hotelRepository.findAll();
    }

    //get hotel by id
    public Hotel getHotelById(Long id) {
        Hotel hotel = hotelRepository.findById(id)
            .orElseThrow(() -> CustomResponseException.ResourceNotFound("Room with this id, doesn't exist"));

        return hotel;
    }

    // add new Hotel
    public Hotel CreateHotel(HotelDTO newHotelDTO) {

        if(newHotelDTO == null){
            throw new IllegalArgumentException("Hotel cannot be null");
        }

        //check existing hotel by name
        boolean exists = hotelRepository.findAll().stream()
            .anyMatch(hotel -> hotel.getName().toLowerCase().equals(newHotelDTO.name().toLowerCase()));
        
        if(exists) {
            throw CustomResponseException.AlreadyExists("Hotel with this name already existe");
        }

        //conversion from DTO to entity
        Hotel newHotel = new Hotel();

        newHotel.setAdress(newHotelDTO.adress());
        newHotel.setName(newHotelDTO.name());
        newHotel.setStars(newHotelDTO.stars());

        hotelRepository.save(newHotel);
        return newHotel;
    }

    public Hotel updateHotel(HotelDTO updatedHotel, Long id) {

        //find the hotel by id
        Hotel existingHotel = hotelRepository.findById(id)
            .orElseThrow(()-> CustomResponseException.ResourceNotFound("Hotel with this id: " + id + "not found!!"));

        existingHotel.setAdress(updatedHotel.adress());
        existingHotel.setName(updatedHotel.name());
        existingHotel.setStars(updatedHotel.stars());

        hotelRepository.save(existingHotel);
        return existingHotel;
    }

    //delete hotel by id
    public void deleteHotelById(Long id){
        Optional<Hotel> hotel = hotelRepository.findById(id);
        if(hotel.isPresent()){
            hotelRepository.deleteById(id);
        }
    }
}