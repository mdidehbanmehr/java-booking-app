package com.example.demo.rest.booking;

import com.example.demo.booking.Booking;
import com.example.demo.booking.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<Map<String, String>> bookRoom(@RequestBody BookingRequest request) {
        Booking booking = bookingService.createBooking(request.getRoomId(), request.getStartDate(), request.getEndDate());
        Map<String, String> response = new HashMap<>();
        response.put("bookingReference", booking.getBookingReference());
        return ResponseEntity.ok(response);
    }

    // 3) Search current bookings: GET /bookings?startDate=yyyy-MM-dd&endDate=yyyy-MM-dd
    @GetMapping
    public List<Booking> searchBookings(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return bookingService.searchBookings(startDate, endDate);
    }

    // 4) Cancel current booking: POST /bookings/{bookingReference}/cancel
    @PostMapping("/{bookingReference}/cancel")
    public ResponseEntity<String> cancelBooking(@PathVariable String bookingReference) {
        bookingService.cancelBooking(bookingReference);
        return ResponseEntity.ok("Booking cancelled");
    }
}
