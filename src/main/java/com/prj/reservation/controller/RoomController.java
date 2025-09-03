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

import com.prj.reservation.dto.RoomDTO;
import com.prj.reservation.entity.Room;
import com.prj.reservation.exception.GlobalResponse;
import com.prj.reservation.service.RoomService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping
    public ResponseEntity<GlobalResponse<List<Room>>> findAllRooms(){
        List<Room> rooms = roomService.getAllRooms();
        return new ResponseEntity<>(new GlobalResponse<>(rooms), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GlobalResponse<Room>> addRoom(@RequestBody @Valid RoomDTO newRoom) {
        Room room = roomService.CreatRoom(newRoom);
        return new ResponseEntity<>(new GlobalResponse<>(room), HttpStatus.CREATED);
    }

    @PutMapping("/{roomId}")
    public ResponseEntity<GlobalResponse<Room>> updateRoom(@PathVariable Long roomId, @RequestBody @Valid RoomDTO roomDTO){
        Room updatedRoom = roomService.updateRoom(roomDTO, roomId);
        return new ResponseEntity<>(new GlobalResponse<>(updatedRoom), HttpStatus.OK);
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long roomId){
        roomService.deleteRoom(roomId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<GlobalResponse<Room>> getRoomById(@PathVariable Long roomId) {
        Room room = roomService.getRoomById(roomId);
        return new ResponseEntity<>(new GlobalResponse<>(room), HttpStatus.OK);
    }


    



}