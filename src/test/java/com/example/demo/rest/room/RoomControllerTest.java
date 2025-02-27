package com.example.demo.rest.room;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAvailableRooms() throws Exception {
        // Choose a date range that does not conflict with the pre-seeded booking
        mockMvc.perform(get("/rooms/available")
                        .param("startDate", "2025-03-10")
                        .param("endDate", "2025-03-15")
                        .param("beds", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}