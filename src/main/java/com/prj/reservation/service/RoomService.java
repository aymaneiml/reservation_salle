package com.prj.reservation.service;

import java.lang.foreign.Linker.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.prj.reservation.dto.RoomDTO;
import com.prj.reservation.entity.Admin;
import com.prj.reservation.entity.Hotel;
import com.prj.reservation.entity.Room;
import com.prj.reservation.exception.CustomResponseException;
import com.prj.reservation.repository.AdminRepository;
import com.prj.reservation.repository.HotelRepository;
import com.prj.reservation.repository.RoomRepository;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private AdminRepository adminRepository;

    //get all rooms
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    //get Room details By id
    public Room getRoomById(Long id) {

        Room room = roomRepository.findById(id)
            .orElseThrow(() -> CustomResponseException.ResourceNotFound("Room with this id, doesn't exist"));

        return room;
    }

    //add new Room
    public Room CreatRoom(RoomDTO newRoomDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        Admin adminConnecte = adminRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Admin not found"));

        if(newRoomDTO == null ){
            throw new IllegalArgumentException("Room cannot be null");
        }

        //check existing room by room_number
        boolean exists = roomRepository.findAll().stream()
            .anyMatch(room -> room.getRoomNumber().equals(newRoomDTO.roomNumber()));

        if(exists){
            throw CustomResponseException.AlreadyExists("Room with this room number already existe");
        }

        //conversion from DTO to entity
        Room room = new Room();

        Hotel hotel = hotelRepository.findById(newRoomDTO.hotelId())
            .orElseThrow(() -> CustomResponseException.ResourceNotFound(
                "Hotel with id: " + newRoomDTO.hotelId() + " not found!"
            ));
        
        room.setAdmin(adminConnecte);
        room.setCapacity(newRoomDTO.capacity());
        room.setDescription(newRoomDTO.description());
        room.setHotel(hotel);
        room.setRoomStatus(newRoomDTO.roomStatus());
        room.setPrice(newRoomDTO.price());
        room.setRoomNumber(newRoomDTO.roomNumber());

        roomRepository.save(room);
        
        return room;
    }

    //update room
    public Room updateRoom(RoomDTO updatedRoom, Long id) {

        //find the room by id
        Room existingRoom = roomRepository.findById(id)
            .orElseThrow(()->CustomResponseException.ResourceNotFound("Room with this id: " + id + "not found!!"));

        Hotel hotel =hotelRepository.findById(updatedRoom.hotelId())
            .orElse(null);

        existingRoom.setCapacity(updatedRoom.capacity());
        existingRoom.setDescription(updatedRoom.description());
        existingRoom.setHotel(hotel);
        existingRoom.setPrice(updatedRoom.price());
        existingRoom.setRoomStatus(updatedRoom.roomStatus());
        existingRoom.setRoomNumber(updatedRoom.roomNumber());

        roomRepository.save(existingRoom);
        return existingRoom;

    }

    // delete room by id
    public void deleteRoom(Long id){
        Optional<Room> room = roomRepository.findById(id);
        if(room.isPresent()){
            roomRepository.deleteById(id);
        }
    }


}