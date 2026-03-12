package com.java.hotel.sling.buscador.infrastructure.rest;

import com.java.hotel.sling.buscador.domain.model.HotelSearchRequest;
import com.java.hotel.sling.buscador.domain.model.SearchCreatedResponse;
import com.java.hotel.sling.buscador.domain.port.in.SearchUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/searches")
@Tag(name = "Search", description = "Hotel Search API")
public class SearchController {

    private final SearchUseCase searchUseCase;

    public SearchController(SearchUseCase searchUseCase) {
        this.searchUseCase = searchUseCase;
    }

    @PostMapping
    @Operation(summary = "Create a new hotel search", description = "Validates that checkIn < checkOut and sends to Kafka")
    public ResponseEntity<SearchCreatedResponse> createSearch(
            @Valid @RequestBody SearchRequestDto dto) {
        HotelSearchRequest request = new HotelSearchRequest(
            dto.hotelId(),
            dto.checkIn(),
            dto.checkOut(),
            dto.ages()
        );
        SearchCreatedResponse response = searchUseCase.createSearch(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgument(IllegalArgumentException ex) {
        return ex.getMessage();
    }
}
