package com.java.hotel.sling.contador.infrastructure.persistence;

import com.java.hotel.sling.contador.domain.model.HotelSearchMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchRepositoryAdapterTest {

    @Mock
    private SearchAvailabilityRepository jpaRepository;

    private SearchRepositoryAdapter searchRepositoryAdapter;

    @BeforeEach
    void setUp() {
        searchRepositoryAdapter = new SearchRepositoryAdapter(jpaRepository);
    }

    @Test
    void save_persistsMessageToDatabase() {
        HotelSearchMessage message = new HotelSearchMessage(
                "test-id",
                "1234aBc",
                LocalDate.of(2023, 12, 29),
                LocalDate.of(2023, 12, 31),
                List.of(30, 29, 1, 3));

        searchRepositoryAdapter.save(message);

        ArgumentCaptor<SearchAvailabilityEntity> entityCaptor = ArgumentCaptor.forClass(SearchAvailabilityEntity.class);
        verify(jpaRepository, times(1)).save(entityCaptor.capture());

        SearchAvailabilityEntity savedEntity = entityCaptor.getValue();
        assertEquals("test-id", savedEntity.getSearchId());
        assertEquals("1234aBc", savedEntity.getHotelId());
    }

    @Test
    void findBySearchId_existingId_returnsMessage() {
        String searchId = "test-id";
        SearchAvailabilityEntity entity = new SearchAvailabilityEntity(
                searchId, "1234aBc",
                LocalDate.of(2023, 12, 29),
                LocalDate.of(2023, 12, 31),
                "30,29,1,3");

        when(jpaRepository.findById(searchId)).thenReturn(Optional.of(entity));

        HotelSearchMessage result = searchRepositoryAdapter.findBySearchId(searchId);

        assertNotNull(result);
        assertEquals(searchId, result.searchId());
        assertEquals("1234aBc", result.hotelId());
    }

    @Test
    void findBySearchId_nonExistingId_returnsNull() {
        String searchId = "non-existing";
        when(jpaRepository.findById(searchId)).thenReturn(Optional.empty());

        HotelSearchMessage result = searchRepositoryAdapter.findBySearchId(searchId);

        assertNull(result);
    }

    @Test
    void countSimilarSearches_matchingSearches_returnsCount() {
        HotelSearchMessage message = new HotelSearchMessage(
                "test-id",
                "1234aBc",
                LocalDate.of(2023, 12, 29),
                LocalDate.of(2023, 12, 31),
                List.of(30, 29));

        when(jpaRepository.countSimilar(
                eq("1234aBc"),
                eq(LocalDate.of(2023, 12, 29)),
                eq(LocalDate.of(2023, 12, 31)),
                eq("30,29")))
                .thenReturn(1L);

        long count = searchRepositoryAdapter.countSimilarSearches(message);

        assertEquals(1L, count);
    }
}
