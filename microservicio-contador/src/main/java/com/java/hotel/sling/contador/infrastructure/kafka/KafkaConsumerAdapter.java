package com.java.hotel.sling.contador.infrastructure.kafka;

import com.java.hotel.sling.contador.domain.model.HotelSearchMessage;
import com.java.hotel.sling.contador.domain.port.out.SearchRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerAdapter.class);
    
    private final SearchRepositoryPort repositoryPort;

    public KafkaConsumerAdapter(SearchRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @KafkaListener(topics = "${app.kafka.topic}", groupId = "hotel-contador-group")
    public void consumeSearch(HotelSearchMessage message) {
        logger.info("Received search message: {}", message.searchId());
        try {
            repositoryPort.save(message);
            logger.info("Search persisted successfully: {}", message.searchId());
        } catch (Exception e) {
            logger.error("Error persisting search: {}", e.getMessage(), e);
        }
    }
}
