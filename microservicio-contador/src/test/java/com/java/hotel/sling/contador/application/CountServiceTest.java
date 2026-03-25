package com.java.hotel.sling.contador.application;

import com.java.hotel.sling.contador.domain.model.CountResponse;
import com.java.hotel.sling.contador.domain.model.HotelSearchMessage;
import com.java.hotel.sling.contador.domain.port.out.SearchRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CountServiceTest {

    @Mock
    private SearchRepositoryPort repositoryPort;

    private CountService countService;

    @BeforeEach
    void setUp() {
        countService = new CountService(repositoryPort);
    }

    @Test
    void getCountBySearchId_existingSearch_returnsCount() {
        String searchId = "test-search-id";
        HotelSearchMessage message = new HotelSearchMessage(
            searchId,
            "1234aBc",
            LocalDate.of(2023, 12, 29),
            LocalDate.of(2023, 12, 31),
            List.of(30, 29, 1, 3)
        );

        when(repositoryPort.findBySearchId(searchId)).thenReturn(message);
        when(repositoryPort.countSimilarSearches(any())).thenReturn(5L);

        CountResponse response = countService.getCountBySearchId(searchId);

        assertEquals(searchId, response.searchId());
        assertEquals(5L, response.count());
        assertEquals("1234aBc", response.search().hotelId());
    }

    @Test
    void getCountBySearchId_nonExistingSearch_throwsException() {
        String searchId = "non-existing-id";

        when(repositoryPort.findBySearchId(searchId)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> countService.getCountBySearchId(searchId));
        verify(repositoryPort, never()).countSimilarSearches(any());
    }

    @Test
    void getCountBySearchId_validSearch_returnsOriginalAgesOrder() {
        String searchId = "test-search-id";
        HotelSearchMessage message = new HotelSearchMessage(
            searchId,
            "1234aBc",
            LocalDate.of(2023, 12, 29),
            LocalDate.of(2023, 12, 31),
            List.of(30, 29, 1, 3)
        );

        when(repositoryPort.findBySearchId(searchId)).thenReturn(message);
        when(repositoryPort.countSimilarSearches(any())).thenReturn(1L);

        CountResponse response = countService.getCountBySearchId(searchId);

        assertEquals(List.of(30, 29, 1, 3), response.search().ages());
    }

    @Test
    void getCountBySearchId_countIncludesReference_returnsCorrectCount() {
        String searchId = "test-search-id";
        HotelSearchMessage message = new HotelSearchMessage(
            searchId,
            "1234aBc",
            LocalDate.of(2023, 12, 29),
            LocalDate.of(2023, 12, 31),
            List.of(30, 29)
        );

        when(repositoryPort.findBySearchId(searchId)).thenReturn(message);
        when(repositoryPort.countSimilarSearches(message)).thenReturn(1L);

        CountResponse response = countService.getCountBySearchId(searchId);

        assertEquals(1L, response.count());
    }
}
