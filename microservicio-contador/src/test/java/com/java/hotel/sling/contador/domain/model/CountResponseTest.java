package com.java.hotel.sling.contador.domain.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CountResponseTest {

    @Test
    void constructor_createsResponseWithAllFields() {
        CountResponse.SearchDetail detail = new CountResponse.SearchDetail(
            "hotel123", "15/01/2024", "20/01/2024", List.of(30, 25));

        CountResponse response = new CountResponse("search-123", detail, 5L);

        assertEquals("search-123", response.searchId());
        assertEquals(5L, response.count());
        assertEquals("hotel123", response.search().hotelId());
        assertEquals("15/01/2024", response.search().checkIn());
        assertEquals("20/01/2024", response.search().checkOut());
        assertEquals(List.of(30, 25), response.search().ages());
    }

    @Test
    void equals_sameValues_returnsTrue() {
        CountResponse.SearchDetail detail1 = new CountResponse.SearchDetail(
            "hotel", "01/01/2024", "05/01/2024", List.of(30));
        CountResponse.SearchDetail detail2 = new CountResponse.SearchDetail(
            "hotel", "01/01/2024", "05/01/2024", List.of(30));

        CountResponse response1 = new CountResponse("search-1", detail1, 5L);
        CountResponse response2 = new CountResponse("search-1", detail2, 5L);

        assertEquals(response1, response2);
    }

    @Test
    void equals_differentValues_returnsFalse() {
        CountResponse.SearchDetail detail = new CountResponse.SearchDetail(
            "hotel", "01/01/2024", "05/01/2024", List.of(30));

        CountResponse response1 = new CountResponse("search-1", detail, 5L);
        CountResponse response2 = new CountResponse("search-2", detail, 10L);

        assertNotEquals(response1, response2);
    }

    @Test
    void searchDetail_toString_containsAllFields() {
        CountResponse.SearchDetail detail = new CountResponse.SearchDetail(
            "hotel123", "15/01/2024", "20/01/2024", List.of(30, 25));

        String result = detail.toString();

        assertTrue(result.contains("hotel123"));
        assertTrue(result.contains("15/01/2024"));
        assertTrue(result.contains("20/01/2024"));
    }
}
