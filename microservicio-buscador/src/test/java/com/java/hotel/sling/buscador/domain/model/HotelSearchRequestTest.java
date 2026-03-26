package com.java.hotel.sling.buscador.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HotelSearchRequestTest {

    @Test
    void constructor_createsRequestWithCorrectValues() {
        LocalDate checkIn = LocalDate.of(2024, 1, 15);
        LocalDate checkOut = LocalDate.of(2024, 1, 20);
        List<Integer> ages = List.of(30, 25, 5);

        HotelSearchRequest request = new HotelSearchRequest("hotel123", checkIn, checkOut, ages);

        assertEquals("hotel123", request.hotelId());
        assertEquals(checkIn, request.checkIn());
        assertEquals(checkOut, request.checkOut());
        assertEquals(ages, request.ages());
    }

    @Test
    void equals_sameValues_returnsTrue() {
        HotelSearchRequest request1 = new HotelSearchRequest(
            "hotel", LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 5), List.of(30));
        HotelSearchRequest request2 = new HotelSearchRequest(
            "hotel", LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 5), List.of(30));

        assertEquals(request1, request2);
    }

    @Test
    void equals_differentValues_returnsFalse() {
        HotelSearchRequest request1 = new HotelSearchRequest(
            "hotel1", LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 5), List.of(30));
        HotelSearchRequest request2 = new HotelSearchRequest(
            "hotel2", LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 5), List.of(30));

        assertNotEquals(request1, request2);
    }

    @Test
    void toString_containsAllFields() {
        HotelSearchRequest request = new HotelSearchRequest(
            "hotel123", LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 5), List.of(30, 25));

        String result = request.toString();

        assertTrue(result.contains("hotel123"));
        assertTrue(result.contains("30"));
        assertTrue(result.contains("25"));
    }
}
