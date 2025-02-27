package com.example.demo.rest.booking;

import com.example.demo.booking.Booking;
import com.example.demo.booking.BookingRepository;
import com.example.demo.room.Room;
import com.example.demo.room.RoomRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @BeforeEach
    public void setup() {
        bookingRepository.deleteAll();
        roomRepository.deleteAll();

        roomRepository.save(new Room( "room1", 2, "expensive room", 1999));
        roomRepository.save(new Room( "room2", 1, "cheap room", 999));
    }

    @Test
    public void testBookRoomSuccess() throws Exception {
        int roomId = roomRepository.findAll().getFirst().getId();

        String requestJson = String.format(
                "{\"roomId\": %d, \"startDate\": \"2025-04-01\", \"endDate\": \"2025-04-05\"}", roomId);

        mockMvc.perform(post("/booking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookingReference").exists());
    }

    @Test
    public void testBookRoomOverlapFailure() throws Exception {
        int roomId = roomRepository.findAll().getFirst().getId();

        String validBooking = String.format(
                "{\"roomId\": %d, \"startDate\": \"2025-03-01\", \"endDate\": \"2025-03-05\"}", roomId);

        mockMvc.perform(post("/booking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validBooking))
                .andExpect(status().isOk());

        String overlappingBooking = String.format(
                "{\"roomId\": %d, \"startDate\": \"2025-03-03\", \"endDate\": \"2025-03-07\"}", roomId);

        mockMvc.perform(post("/booking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(overlappingBooking))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void testSearchBookings() throws Exception {
        int roomId = roomRepository.findAll().getFirst().getId();

        String requestJson = String.format(
                "{\"roomId\": %d, \"startDate\": \"2025-03-02\", \"endDate\": \"2025-03-06\"}", roomId);

        mockMvc.perform(post("/booking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());

        mockMvc.perform(get("/booking")
                        .param("startDate", "2025-03-01")
                        .param("endDate", "2025-03-10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$.[0].room.id").value(roomId));
    }

    @Test
    public void testCancelBooking() throws Exception {
        int roomId = roomRepository.findAll().get(0).getId();

        String requestJson = String.format(
                "{\"roomId\": %d, \"startDate\": \"2025-05-01\", \"endDate\": \"2025-05-05\"}", roomId);

        MvcResult result = mockMvc.perform(post("/booking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(responseContent);
        String bookingReference = jsonNode.get("bookingReference").asText();

        mockMvc.perform(post("/booking/" + bookingReference + "/cancel"))
                .andExpect(status().isOk())
                .andExpect(content().string("Booking cancelled"));

        Optional<Booking> cancelledBooking = bookingRepository.findByBookingReference(bookingReference);
        assertTrue(cancelledBooking.isEmpty());
    }
}