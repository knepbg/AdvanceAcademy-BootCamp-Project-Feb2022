package com.jbcamp1.moonlighthotel.repository;

import com.jbcamp1.moonlighthotel.model.RoomReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomReservationRepository extends JpaRepository<RoomReservation, Long> {

    @Query(value = "SELECT COUNT(rr) FROM RoomReservation rr WHERE " +
            "(rr.room.id = ?3 and rr.startDate BETWEEN ?1 and ?2) " +
            "or (rr.room.id = ?3 and rr.endDate BETWEEN ?1 and ?2) " +
            "or (rr.room.id = ?3 and ?1 BETWEEN rr.startDate and rr.endDate) " +
            "or (rr.room.id = ?3 and ?2 BETWEEN rr.startDate and rr.endDate) " +
            "GROUP BY rr.room.id")
    Integer countOfRoomReservationsByPeriod(Instant startDate, Instant endDate, Long rid);

    List<RoomReservation> findAllRoomReservationByRoomId(Long id);

    Optional<RoomReservation> findRoomReservationByIdAndUserId(Long rid, Long uid);

    Optional<RoomReservation> findRoomReservationByIdAndRoomId(Long rid, Long id);

}
