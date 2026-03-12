package com.java.hotel.sling.buscador.domain.port.out;

import com.java.hotel.sling.buscador.domain.model.HotelSearchMessage;

public interface KafkaProducerPort {
    void sendSearch(HotelSearchMessage message);
}
