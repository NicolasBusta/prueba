package com.java.hotel.sling.contador.infrastructure.persistence;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class SearchAvailabilityEntityTest {

    @Test
    void constructor_createsEntityWithAllFields() {
        LocalDate checkIn = LocalDate.of(2024, 1, 15);
        LocalDate checkOut = LocalDate.of(2024, 1, 20);

        SearchAvailabilityEntity entity = new SearchAvailabilityEntity(
            "search-123", "hotel123", checkIn, checkOut, "30,25,5");

        assertEquals("search-123", entity.getSearchId());
        assertEquals("hotel123", entity.getHotelId());
        assertEquals(checkIn, entity.getCheckIn());
        assertEquals(checkOut, entity.getCheckOut());
        assertEquals("30,25,5", entity.getAges());
    }

    @Test
    void defaultConstructor_createsEmptyEntity() {
        SearchAvailabilityEntity entity = new SearchAvailabilityEntity();

        assertNull(entity.getSearchId());
        assertNull(entity.getHotelId());
        assertNull(entity.getCheckIn());
        assertNull(entity.getCheckOut());
        assertNull(entity.getAges());
    }

    @Test
    void constructor_withNullAges_handlesCorrectly() {
        LocalDate checkIn = LocalDate.of(2024, 1, 15);
        LocalDate checkOut = LocalDate.of(2024, 1, 20);

        SearchAvailabilityEntity entity = new SearchAvailabilityEntity(
            "search-123", "hotel123", checkIn, checkOut, null);

        assertEquals("search-123", entity.getSearchId());
        assertNull(entity.getAges());
    }
}
