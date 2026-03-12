package com.java.hotel.sling.contador.infrastructure.persistence;

import com.java.hotel.sling.contador.domain.model.HotelSearchMessage;
import com.java.hotel.sling.contador.domain.model.SearchAvailabilityEntity;
import com.java.hotel.sling.contador.domain.port.out.SearchRepositoryPort;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
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
        List<SearchAvailabilityEntity> allSearches = jpaRepository.findAll();

        Set<Integer> agesSet = message.ages() != null
                ? message.ages().stream().collect(Collectors.toSet())
                : Collections.emptySet();

        return allSearches.stream()
                .filter(entity -> entity.getHotelId().equals(message.hotelId()))
                .filter(entity -> entity.getCheckIn().equals(message.checkIn()))
                .filter(entity -> entity.getCheckOut().equals(message.checkOut()))
                .filter(entity -> {
                    Set<Integer> entityAges = parseAges(entity.getAges());
                    return entityAges.equals(agesSet);
                })
                .count();
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

    private Set<Integer> parseAges(String ages) {
        if (ages == null || ages.isBlank()) {
            return Collections.emptySet();
        }
        return Arrays.stream(ages.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Integer::parseInt)
                .collect(Collectors.toSet());
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
