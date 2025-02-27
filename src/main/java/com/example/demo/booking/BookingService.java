package com.example.demo.booking;

import com.example.demo.room.Room;
import com.example.demo.room.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    public Booking createBooking(Long roomId, LocalDate startDate, LocalDate endDate) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        List<Booking> overlapping = bookingRepository.findOverlappingBookings(roomId, startDate, endDate);
        if (!overlapping.isEmpty()) {
            throw new RuntimeException("Room is already booked for the selected date range");
        }

        Booking booking = new Booking();
        booking.setRoom(room);
        booking.setStartDate(startDate);
        booking.setEndDate(endDate);
        booking.setBookingReference(UUID.randomUUID().toString());
        return bookingRepository.save(booking);
    }

    public List<Booking> searchBookings(LocalDate startDate, LocalDate endDate) {
        return bookingRepository.findBookingsInDateRange(startDate, endDate);
    }

    public void cancelBooking(String bookingReference) {
        Booking booking = bookingRepository.findByBookingReference(bookingReference)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        bookingRepository.delete(booking);
    }
}
