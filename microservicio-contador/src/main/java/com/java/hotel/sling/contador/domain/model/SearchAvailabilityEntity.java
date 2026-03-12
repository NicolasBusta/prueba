package com.java.hotel.sling.contador.domain.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "search_availability")
public class SearchAvailabilityEntity {

    @Id
    private String searchId;

    @Column(nullable = false)
    private String hotelId;

    @Column(nullable = false)
    private LocalDate checkIn;

    @Column(nullable = false)
    private LocalDate checkOut;

    @Column(columnDefinition = "TEXT")
    private String ages;

    public SearchAvailabilityEntity() {
    }

    public SearchAvailabilityEntity(String searchId, String hotelId, LocalDate checkIn, LocalDate checkOut,
            String ages) {
        this.searchId = searchId;
        this.hotelId = hotelId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.ages = ages;
    }

    public String getSearchId() {
        return searchId;
    }

    public String getHotelId() {
        return hotelId;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public String getAges() {
        return ages;
    }
}
