package com.java.hotel.sling.contador.domain.model;

import java.util.List;

public record CountResponse(
        String searchId,
        SearchDetail search,
        long count) {
    public record SearchDetail(
            String hotelId,
            String checkIn,
            String checkOut,
            List<Integer> ages) {
    }
}
