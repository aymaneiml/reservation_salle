package com.prj.reservation.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.prj.reservation.entity.Reservation;
import com.prj.reservation.entity.ReservationStatus;
import com.prj.reservation.entity.Room;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>{

    @Query("""
        SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END
        FROM Reservation r
        WHERE r.room.id = :roomId
          AND NOT (r.endDate <= :startDate OR r.startDate >= :endDate)
    """)
    boolean existsByRoomIdAndPeriodOverlap(Long roomId, LocalDate startDate, LocalDate endDate);

    List<Reservation> findByRoomAndStatus(Room room, ReservationStatus status);

    @Query("SELECT r FROM Reservation r WHERE r.endDate < :date AND r.status = :status")
    List<Reservation> findByEndDateBeforeAndStatus(@Param("date") LocalDate date,
                                               @Param("status") ReservationStatus status);

    List<Reservation> findByClient_Id(UUID clientId);
}