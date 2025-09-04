package com.prj.reservation.service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.prj.reservation.dto.ReservationDTO;
import com.prj.reservation.dto.ReservationResponseDTO;
import com.prj.reservation.entity.Client;
import com.prj.reservation.entity.Reservation;
import com.prj.reservation.entity.ReservationStatus;
import com.prj.reservation.entity.Room;
import com.prj.reservation.entity.RoomStatus;
import com.prj.reservation.exception.CustomResponseException;
import com.prj.reservation.repository.ClientRepository;
import com.prj.reservation.repository.ReservationRepository;
import com.prj.reservation.repository.RoomRepository;

import jakarta.transaction.Transactional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RoomRepository roomRepository;

    //get my reservation
    public List<ReservationResponseDTO> getMyReservations() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Client clientConnecte = clientRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        List<Reservation> myReservations = reservationRepository.findAll().stream()
                .filter(resv -> resv.getClient().getId().equals(clientConnecte.getId()))
                .collect(Collectors.toList());

        return myReservations.stream().map(reservation -> {
            String message = "Votre réservation est " + reservation.getStatus();
            return new ReservationResponseDTO(
                    reservation.getId(),
                    reservation.getClient().getFirstName(),
                    reservation.getClient().getLastName(),
                    reservation.getRoom().getRoomNumber(),
                    reservation.getStatus(),
                    reservation.getStartDate(),
                    reservation.getEndDate(),
                    message
            );
        }).collect(Collectors.toList());
    }

    //get all reseravtion for admin
    public List<ReservationResponseDTO> getAllReservations(){
        List<Reservation> reservations = reservationRepository.findAll();
        
        return reservations.stream().map( resv -> {
            String message = "Tous les reservations en detailles";
            return new ReservationResponseDTO(
                    resv.getId(),
                    resv.getClient().getFirstName(),
                    resv.getClient().getLastName(),
                    resv.getRoom().getRoomNumber(),
                    resv.getStatus(),
                    resv.getStartDate(),
                    resv.getEndDate(),
                    message
            );
        }).collect(Collectors.toList());

    }

    //ajouter un reservation
    @Transactional
    public ReservationResponseDTO createReservation(ReservationDTO dto) {
        // 1. Récupérer le client connecté
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Client clientConnecte = clientRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        // 2. Récupérer la chambre
        Room room = roomRepository.findById(dto.roomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        // 3. Créer la réservation
        Reservation reservation = new Reservation();
        reservation.setClient(clientConnecte);
        reservation.setRoom(room);
        reservation.setStartDate(dto.startDate());
        reservation.setEndDate(dto.endDate());

        String message;

        // 4. Logique de disponibilité
        if (room.getRoomStatus() == RoomStatus.AVAILABLE) {
            reservation.setStatus(ReservationStatus.CONFIRMED);
            room.setRoomStatus(RoomStatus.BOOKED);
            message = "Réservation confirmée pour la chambre " + room.getRoomNumber();
        } else {
            reservation.setStatus(ReservationStatus.PENDING);
            message = "La chambre " + room.getRoomNumber() + " n'est pas disponible. Votre réservation est en attente.";
        }

        // 5. Sauvegarde
        reservationRepository.save(reservation);
        roomRepository.save(room);

      // 6. Retourner un DTO complet avec message
        return new ReservationResponseDTO(
                reservation.getId(),
                clientConnecte.getFirstName(),  
                clientConnecte.getLastName(),
                room.getRoomNumber(),
                reservation.getStatus(),
                reservation.getStartDate(),
                reservation.getEndDate(),
                message
        );
    }

    /**
     * Méthode à appeler lorsqu'une chambre devient disponible
     * -> Met à jour toutes les réservations en attente pour cette chambre
     */
    @Transactional
    public void updatePendingReservationsForRoom(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        if (room.getRoomStatus() == RoomStatus.AVAILABLE) {
            List<Reservation> pendingReservations =
                    reservationRepository.findByRoomAndStatus(room, ReservationStatus.PENDING);

            if (!pendingReservations.isEmpty()) {
                // Prendre la plus ancienne réservation en attente
                Reservation reservation = pendingReservations.get(0);
                reservation.setStatus(ReservationStatus.CONFIRMED);
                room.setRoomStatus(RoomStatus.BOOKED);
                reservationRepository.save(reservation);
                roomRepository.save(room);
            }
        }
    }


    //confirmation de la reservation par l'admin
    public void confirmedReservation(Long id){
        Reservation reservation = reservationRepository.findById(id)
            .orElseThrow(() -> CustomResponseException.ResourceNotFound("Resevation with this id do not exist!!"));
        
        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservationRepository.save(reservation);
    }

    //annulation de la reservation par l'admin
    public void cancelledReservation(Long id){
        Reservation reservation = reservationRepository.findById(id)
            .orElseThrow(() -> CustomResponseException.ResourceNotFound("Resevation with this id do not exist!!"));
        
        reservation.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(reservation);
    }
}