package com.example.demo.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("SELECT r FROM Room r WHERE r.beds >= :beds AND r.id NOT IN " +
            "(SELECT b.room.id FROM Booking b WHERE " +
            "(:startDate < b.endDate AND :endDate > b.startDate))")
    List<Room> findAvailableRooms(@Param("beds") int beds,
                                  @Param("startDate") LocalDate startDate,
                                  @Param("endDate") LocalDate endDate);
}
