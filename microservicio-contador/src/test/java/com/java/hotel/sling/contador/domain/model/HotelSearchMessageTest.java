package com.java.hotel.sling.contador.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HotelSearchMessageTest {

    @Test
    void constructor_createsMessageWithAllFields() {
        LocalDate checkIn = LocalDate.of(2024, 1, 15);
        LocalDate checkOut = LocalDate.of(2024, 1, 20);
        List<Integer> ages = List.of(30, 25, 5);

        HotelSearchMessage message = new HotelSearchMessage("search-123", "hotel123", checkIn, checkOut, ages);

        assertEquals("search-123", message.searchId());
        assertEquals("hotel123", message.hotelId());
        assertEquals(checkIn, message.checkIn());
        assertEquals(checkOut, message.checkOut());
        assertEquals(ages, message.ages());
    }

    @Test
    void equals_sameValues_returnsTrue() {
        HotelSearchMessage message1 = new HotelSearchMessage(
            "search-1", "hotel", LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 5), List.of(30));
        HotelSearchMessage message2 = new HotelSearchMessage(
            "search-1", "hotel", LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 5), List.of(30));

        assertEquals(message1, message2);
        assertEquals(message1.hashCode(), message2.hashCode());
    }

    @Test
    void equals_differentSearchId_returnsFalse() {
        HotelSearchMessage message1 = new HotelSearchMessage(
            "search-1", "hotel", LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 5), List.of(30));
        HotelSearchMessage message2 = new HotelSearchMessage(
            "search-2", "hotel", LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 5), List.of(30));

        assertNotEquals(message1, message2);
    }

    @Test
    void toString_containsAllFields() {
        HotelSearchMessage message = new HotelSearchMessage(
            "search-123", "hotel123", LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 5), List.of(30, 25));

        String result = message.toString();

        assertTrue(result.contains("search-123"));
        assertTrue(result.contains("hotel123"));
    }
}
