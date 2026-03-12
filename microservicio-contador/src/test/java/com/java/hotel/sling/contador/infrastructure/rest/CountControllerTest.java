package com.java.hotel.sling.contador.infrastructure.rest;

import com.java.hotel.sling.contador.domain.model.CountResponse;
import com.java.hotel.sling.contador.domain.port.in.CountUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CountControllerTest {

    @Mock
    private CountUseCase countUseCase;

    @InjectMocks
    private CountController countController;

    @Test
    void getCount_existingSearch_returnsCountResponse() {
        String searchId = "test-search-id";
        CountResponse.SearchDetail detail = new CountResponse.SearchDetail(
            "1234aBc", "2023-12-29", "2023-12-31", List.of(1, 3, 29, 30)
        );
        CountResponse mockResponse = new CountResponse(searchId, detail, 5L);

        when(countUseCase.getCountBySearchId(searchId)).thenReturn(mockResponse);

        ResponseEntity<CountResponse> response = countController.getCount(searchId);

        assertEquals(200, response.getStatusCode().value());
        CountResponse body = response.getBody();
        assertNotNull(body);
        assertEquals(searchId, body.searchId());
        assertEquals(5L, body.count());
    }

    @Test
    void handleNotFound_returnsErrorMessage() {
        String errorMessage = "Search not found with id: test-id";
        IllegalArgumentException exception = new IllegalArgumentException(errorMessage);

        String result = countController.handleNotFound(exception);

        assertEquals(errorMessage, result);
    }
}
