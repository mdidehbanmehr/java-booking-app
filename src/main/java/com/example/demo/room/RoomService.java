package com.example.demo.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;


@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    public List<Room> getAvailableRooms(int beds, LocalDate startDate, LocalDate endDate) {
        return roomRepository.findAvailableRooms(beds, startDate, endDate);
    }

}