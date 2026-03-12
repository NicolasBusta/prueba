package com.java.hotel.sling.buscador.infrastructure.rest;

import com.java.hotel.sling.buscador.domain.model.HotelSearchRequest;
import com.java.hotel.sling.buscador.domain.model.SearchCreatedResponse;
import com.java.hotel.sling.buscador.domain.port.in.SearchUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchControllerTest {

    @Mock
    private SearchUseCase searchUseCase;

    @InjectMocks
    private SearchController searchController;

    @Test
    void createSearch_validRequest_returnsCreatedResponse() {
        LocalDate checkIn = LocalDate.of(2023, 12, 29);
        LocalDate checkOut = LocalDate.of(2023, 12, 31);
        SearchRequestDto requestDto = new SearchRequestDto(
            "1234aBc", checkIn, checkOut, List.of(30, 29, 1, 3)
        );
        
        SearchCreatedResponse mockResponse = new SearchCreatedResponse("test-uuid");
        when(searchUseCase.createSearch(any(HotelSearchRequest.class))).thenReturn(mockResponse);

        ResponseEntity<SearchCreatedResponse> response = searchController.createSearch(requestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        SearchCreatedResponse body = response.getBody();
        assertNotNull(body);
        assertEquals("test-uuid", body.searchId());
    }

    @Test
    void handleIllegalArgument_returnsBadRequestMessage() {
        String errorMessage = "checkIn must be before checkOut";
        IllegalArgumentException exception = new IllegalArgumentException(errorMessage);

        String result = searchController.handleIllegalArgument(exception);

        assertEquals(errorMessage, result);
    }
}
