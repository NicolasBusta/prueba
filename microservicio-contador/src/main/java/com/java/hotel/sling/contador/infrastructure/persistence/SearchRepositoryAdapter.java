package com.java.hotel.sling.contador.infrastructure.persistence;

import com.java.hotel.sling.contador.domain.model.HotelSearchMessage;
import com.java.hotel.sling.contador.domain.port.out.SearchRepositoryPort;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SearchRepositoryAdapter implements SearchRepositoryPort {

    private final SearchAvailabilityRepository jpaRepository;

    public SearchRepositoryAdapter(SearchAvailabilityRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(HotelSearchMessage message) {
        String agesString = message.ages() != null
                ? message.ages().stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(","))
                : "";

        SearchAvailabilityEntity entity = new SearchAvailabilityEntity(
                message.searchId(),
                message.hotelId(),
                message.checkIn(),
                message.checkOut(),
                agesString);

        jpaRepository.save(entity);
    }

    @Override
    public long countSimilarSearches(HotelSearchMessage message) {
        String agesString = message.ages() != null
                ? message.ages().stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(","))
                : "";

        return jpaRepository.countSimilar(
                message.hotelId(),
                message.checkIn(),
                message.checkOut(),
                agesString);
    }

    @Override
    public HotelSearchMessage findBySearchId(String searchId) {
        return jpaRepository.findById(searchId)
                .map(entity -> new HotelSearchMessage(
                        entity.getSearchId(),
                        entity.getHotelId(),
                        entity.getCheckIn(),
                        entity.getCheckOut(),
                        parseAgesList(entity.getAges())))
                .orElse(null);
    }

    private List<Integer> parseAgesList(String ages) {
        if (ages == null || ages.isBlank()) {
            return Collections.emptyList();
        }
        return Arrays.stream(ages.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }
}
