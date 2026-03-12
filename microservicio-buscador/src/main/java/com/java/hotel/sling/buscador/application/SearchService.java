package com.java.hotel.sling.buscador.application;

import com.java.hotel.sling.buscador.domain.model.HotelSearchMessage;
import com.java.hotel.sling.buscador.domain.model.HotelSearchRequest;
import com.java.hotel.sling.buscador.domain.model.SearchCreatedResponse;
import com.java.hotel.sling.buscador.domain.port.in.SearchUseCase;
import com.java.hotel.sling.buscador.domain.port.out.KafkaProducerPort;

import java.time.LocalDate;
import java.util.UUID;

public class SearchService implements SearchUseCase {

    private final KafkaProducerPort kafkaProducerPort;

    public SearchService(KafkaProducerPort kafkaProducerPort) {
        this.kafkaProducerPort = kafkaProducerPort;
    }

    @Override
    public SearchCreatedResponse createSearch(HotelSearchRequest request) {
        validateCheckInBeforeCheckOut(request.checkIn(), request.checkOut());
        
        String searchId = UUID.randomUUID().toString();
        
        HotelSearchMessage message = new HotelSearchMessage(
            searchId,
            request.hotelId(),
            request.checkIn(),
            request.checkOut(),
            request.ages()
        );
        
        kafkaProducerPort.sendSearch(message);
        
        return new SearchCreatedResponse(searchId);
    }

    private void validateCheckInBeforeCheckOut(LocalDate checkIn, LocalDate checkOut) {
        if (!checkIn.isBefore(checkOut)) {
            throw new IllegalArgumentException("checkIn must be before checkOut");
        }
    }
}
