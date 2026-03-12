package com.java.hotel.sling.buscador.infrastructure.rest;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record SearchRequestDto(
    @NotBlank(message = "hotelId is required")
    String hotelId,
    @NotNull(message = "checkIn is required")
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate checkIn,
    @NotNull(message = "checkOut is required")
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate checkOut,
    @NotEmpty(message = "ages is required")
    List<Integer> ages
) {}
