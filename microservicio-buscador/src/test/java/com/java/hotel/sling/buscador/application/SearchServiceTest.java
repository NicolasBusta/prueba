package com.java.hotel.sling.buscador.application;

import com.java.hotel.sling.buscador.domain.model.HotelSearchMessage;
import com.java.hotel.sling.buscador.domain.model.HotelSearchRequest;
import com.java.hotel.sling.buscador.domain.model.SearchCreatedResponse;
import com.java.hotel.sling.buscador.domain.port.out.KafkaProducerPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

    @Mock
    private KafkaProducerPort kafkaProducerPort;

    private SearchService searchService;

    @BeforeEach
    void setUp() {
        searchService = new SearchService(kafkaProducerPort);
    }

    @Test
    void createSearch_validRequest_sendsToKafkaAndReturnsSearchId() {
        LocalDate checkIn = LocalDate.of(2023, 12, 29);
        LocalDate checkOut = LocalDate.of(2023, 12, 31);
        HotelSearchRequest request = new HotelSearchRequest("1234aBc", checkIn, checkOut, List.of(30, 29, 1, 3));

        SearchCreatedResponse response = searchService.createSearch(request);

        assertNotNull(response.searchId());
        verify(kafkaProducerPort, times(1)).sendSearch(any(HotelSearchMessage.class));
    }

    @Test
    void createSearch_checkInEqualsCheckOut_throwsException() {
        LocalDate checkIn = LocalDate.of(2023, 12, 29);
        LocalDate checkOut = LocalDate.of(2023, 12, 29);
        HotelSearchRequest request = new HotelSearchRequest("1234aBc", checkIn, checkOut, List.of(30, 29));

        assertThrows(IllegalArgumentException.class, () -> searchService.createSearch(request));
        verify(kafkaProducerPort, never()).sendSearch(any());
    }

    @Test
    void createSearch_checkInAfterCheckOut_throwsException() {
        LocalDate checkIn = LocalDate.of(2023, 12, 31);
        LocalDate checkOut = LocalDate.of(2023, 12, 29);
        HotelSearchRequest request = new HotelSearchRequest("1234aBc", checkIn, checkOut, List.of(30, 29));

        assertThrows(IllegalArgumentException.class, () -> searchService.createSearch(request));
        verify(kafkaProducerPort, never()).sendSearch(any());
    }

    @Test
    void createSearch_validRequest_sendsCorrectMessage() {
        LocalDate checkIn = LocalDate.of(2023, 12, 29);
        LocalDate checkOut = LocalDate.of(2023, 12, 31);
        List<Integer> ages = List.of(30, 29, 1, 3);
        HotelSearchRequest request = new HotelSearchRequest("1234aBc", checkIn, checkOut, ages);

        ArgumentCaptor<HotelSearchMessage> messageCaptor = ArgumentCaptor.forClass(HotelSearchMessage.class);
        searchService.createSearch(request);

        verify(kafkaProducerPort).sendSearch(messageCaptor.capture());
        HotelSearchMessage capturedMessage = messageCaptor.getValue();

        assertEquals("1234aBc", capturedMessage.hotelId());
        assertEquals(checkIn, capturedMessage.checkIn());
        assertEquals(checkOut, capturedMessage.checkOut());
        assertEquals(ages, capturedMessage.ages());
    }
}
