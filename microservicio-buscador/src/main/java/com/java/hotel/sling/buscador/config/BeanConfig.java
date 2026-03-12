package com.java.hotel.sling.buscador.config;

import com.java.hotel.sling.buscador.application.SearchService;
import com.java.hotel.sling.buscador.domain.model.HotelSearchMessage;
import com.java.hotel.sling.buscador.domain.port.in.SearchUseCase;
import com.java.hotel.sling.buscador.domain.port.out.KafkaProducerPort;
import com.java.hotel.sling.buscador.infrastructure.kafka.KafkaProducerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class BeanConfig {

    @Bean
    public SearchUseCase searchUseCase(KafkaProducerPort kafkaProducerPort) {
        return new SearchService(kafkaProducerPort);
    }

    @Bean
    public KafkaProducerPort kafkaProducerPort(
            KafkaTemplate<String, HotelSearchMessage> kafkaTemplate,
            @Value("${app.kafka.topic}") String topic) {
        return new KafkaProducerAdapter(kafkaTemplate, topic);
    }
}
