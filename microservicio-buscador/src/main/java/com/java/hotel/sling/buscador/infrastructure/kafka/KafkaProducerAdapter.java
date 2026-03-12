package com.java.hotel.sling.buscador.infrastructure.kafka;

import com.java.hotel.sling.buscador.domain.model.HotelSearchMessage;
import com.java.hotel.sling.buscador.domain.port.out.KafkaProducerPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducerAdapter implements KafkaProducerPort {

    private final KafkaTemplate<String, HotelSearchMessage> kafkaTemplate;
    private final String topic;

    public KafkaProducerAdapter(
            KafkaTemplate<String, HotelSearchMessage> kafkaTemplate,
            @Value("${app.kafka.topic}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @Override
    public void sendSearch(HotelSearchMessage message) {
        kafkaTemplate.send(topic, message.searchId(), message);
    }
}
