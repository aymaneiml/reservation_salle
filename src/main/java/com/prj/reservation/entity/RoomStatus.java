package com.prj.reservation.entity;

public enum RoomStatus {
    AVAILABLE,      // Chambre disponible
    BOOKED,         // Chambre réservée mais pas encore occupée
    OCCUPIED,       // Chambre actuellement occupée
    CLEANING,       // Chambre en cours de nettoyage
    MAINTENANCE,    // Chambre en maintenance / réparation
    OUT_OF_SERVICE  // Chambre temporairement indisponible
}