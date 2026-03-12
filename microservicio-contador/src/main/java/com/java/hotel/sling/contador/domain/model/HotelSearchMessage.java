package com.java.hotel.sling.contador.domain.model;

import java.time.LocalDate;
import java.util.List;

public record HotelSearchMessage(
    String searchId,
    String hotelId,
    LocalDate checkIn,
    LocalDate checkOut,
    List<Integer> ages
) {}
