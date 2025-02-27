package com.example.demo.rest.room;

import com.example.demo.room.Room;
import com.example.demo.room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomservice;

    // GET /rooms/available?startDate=yyyy-MM-dd&endDate=yyyy-MM-dd&beds=2
    @GetMapping("/available")
    public List<Room> getAvailableRooms(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam("beds") int beds) {
        return roomservice.getAvailableRooms(beds, startDate, endDate);
    }
}