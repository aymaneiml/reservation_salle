package com.prj.reservation.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.prj.reservation.entity.Reservation;
import com.prj.reservation.entity.ReservationStatus;
import com.prj.reservation.entity.Room;
import com.prj.reservation.entity.RoomStatus;
import com.prj.reservation.repository.ReservationRepository;
import com.prj.reservation.repository.RoomRepository;

import jakarta.transaction.Transactional;

@Service
public class ScheduledTasksService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RoomRepository roomRepository;



    @Scheduled(cron = "0 0 0 * * *") // Tous les jours à minuit
    @Transactional
    public void libererChambresExpirees() {
        LocalDate today = LocalDate.now();

        List<Reservation> reservationsExpirees = reservationRepository
                .findByEndDateBeforeAndStatus(today, ReservationStatus.CONFIRMED);

        for (Reservation reservation : reservationsExpirees) {
            Room room = reservation.getRoom();
            room.setRoomStatus(RoomStatus.AVAILABLE);
            roomRepository.save(room);
        }

        if (!reservationsExpirees.isEmpty()) {
            System.out.println(reservationsExpirees.size() + " chambres libérées automatiquement");
        }
    }
}