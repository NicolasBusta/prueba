package com.java.hotel.sling.contador.domain.port.out;

import com.java.hotel.sling.contador.domain.model.HotelSearchMessage;

public interface SearchRepositoryPort {
    void save(HotelSearchMessage message);
    long countSimilarSearches(HotelSearchMessage message);
    HotelSearchMessage findBySearchId(String searchId);
}
