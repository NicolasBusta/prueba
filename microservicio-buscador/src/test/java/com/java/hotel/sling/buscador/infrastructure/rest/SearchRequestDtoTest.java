package com.java.hotel.sling.buscador.infrastructure.rest;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SearchRequestDtoTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void constructor_createsDtoWithCorrectValues() {
        LocalDate checkIn = LocalDate.of(2024, 1, 15);
        LocalDate checkOut = LocalDate.of(2024, 1, 20);
        List<Integer> ages = List.of(30, 25, 5);

        SearchRequestDto dto = new SearchRequestDto("hotel123", checkIn, checkOut, ages);

        assertEquals("hotel123", dto.hotelId());
        assertEquals(checkIn, dto.checkIn());
        assertEquals(checkOut, dto.checkOut());
        assertEquals(ages, dto.ages());
    }

    @Test
    void validate_hotelIdBlank_failsValidation() {
        SearchRequestDto dto = new SearchRequestDto(
            "", LocalDate.now(), LocalDate.now().plusDays(1), List.of(30));

        var violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("hotelId")));
    }

    @Test
    void validate_checkInNull_failsValidation() {
        SearchRequestDto dto = new SearchRequestDto(
            "hotel123", null, LocalDate.now().plusDays(1), List.of(30));

        var violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
    }

    @Test
    void validate_agesEmpty_failsValidation() {
        SearchRequestDto dto = new SearchRequestDto(
            "hotel123", LocalDate.now(), LocalDate.now().plusDays(1), List.of());

        var violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
    }

    @Test
    void validate_validDto_passesValidation() {
        SearchRequestDto dto = new SearchRequestDto(
            "hotel123", LocalDate.now(), LocalDate.now().plusDays(1), List.of(30, 25));

        var violations = validator.validate(dto);

        assertTrue(violations.isEmpty());
    }
}
