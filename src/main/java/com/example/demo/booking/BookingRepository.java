package com.example.demo.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findByBookingReference(String bookingReference);

    @Query("SELECT b FROM Booking b WHERE " +
            "(:startDate < b.endDate AND :endDate > b.startDate)")
    List<Booking> findBookingsInDateRange(@Param("startDate") LocalDate startDate,
                                          @Param("endDate") LocalDate endDate);

    @Query("SELECT b FROM Booking b WHERE b.room.id = :roomId AND " +
            "(:startDate < b.endDate AND :endDate > b.startDate)")
    List<Booking> findOverlappingBookings(@Param("roomId") Long roomId,
                                          @Param("startDate") LocalDate startDate,
                                          @Param("endDate") LocalDate endDate);
}
