package com.java.hotel.sling.contador.application;

import com.java.hotel.sling.contador.domain.model.CountResponse;
import com.java.hotel.sling.contador.domain.model.HotelSearchMessage;
import com.java.hotel.sling.contador.domain.port.in.CountUseCase;
import com.java.hotel.sling.contador.domain.port.out.SearchRepositoryPort;

import java.util.Collections;
import java.util.List;

public class CountService implements CountUseCase {

    private final SearchRepositoryPort repositoryPort;

    public CountService(SearchRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public CountResponse getCountBySearchId(String searchId) {
        HotelSearchMessage referenceSearch = repositoryPort.findBySearchId(searchId);

        if (referenceSearch == null) {
            throw new IllegalArgumentException("Search not found with id: " + searchId);
        }

        long count = repositoryPort.countSimilarSearches(referenceSearch);

        List<Integer> agesList = referenceSearch.ages() != null
                ? referenceSearch.ages()
                : Collections.emptyList();

        CountResponse.SearchDetail detail = new CountResponse.SearchDetail(
                referenceSearch.hotelId(),
                referenceSearch.checkIn().toString(),
                referenceSearch.checkOut().toString(),
                agesList);

        return new CountResponse(searchId, detail, count);
    }
}
