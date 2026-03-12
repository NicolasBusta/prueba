package com.java.hotel.sling.contador.infrastructure.rest;

import com.java.hotel.sling.contador.domain.model.CountResponse;
import com.java.hotel.sling.contador.domain.port.in.CountUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/searches")
@Tag(name = "Count", description = "Hotel Search Count API")
public class CountController {

    private final CountUseCase countUseCase;

    public CountController(CountUseCase countUseCase) {
        this.countUseCase = countUseCase;
    }

    @GetMapping("/count")
    @Operation(summary = "Get search count", description = "Returns the number of similar searches")
    public ResponseEntity<CountResponse> getCount(@RequestParam String searchId) {
        CountResponse response = countUseCase.getCountBySearchId(searchId);
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @org.springframework.web.bind.annotation.ResponseStatus(org.springframework.http.HttpStatus.NOT_FOUND)
    public String handleNotFound(IllegalArgumentException ex) {
        return ex.getMessage();
    }
}
