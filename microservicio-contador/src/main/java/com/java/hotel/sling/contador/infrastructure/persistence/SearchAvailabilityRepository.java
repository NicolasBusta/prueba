package com.java.hotel.sling.contador.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;

@Repository
public interface SearchAvailabilityRepository extends JpaRepository<SearchAvailabilityEntity, String> {

    @Query("SELECT COUNT(s) FROM SearchAvailabilityEntity s " +
            "WHERE s.hotelId = :hotelId " +
            "AND s.checkIn = :checkIn " +
            "AND s.checkOut = :checkOut " +
            "AND s.ages = :ages")
    long countSimilar(
            @Param("hotelId") String hotelId,
            @Param("checkIn") LocalDate checkIn,
            @Param("checkOut") LocalDate checkOut,
            @Param("ages") String ages);
}