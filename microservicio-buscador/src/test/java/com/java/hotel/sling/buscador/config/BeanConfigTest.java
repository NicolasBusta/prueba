package com.java.hotel.sling.buscador.config;

import com.java.hotel.sling.buscador.application.SearchService;
import com.java.hotel.sling.buscador.domain.port.in.SearchUseCase;
import com.java.hotel.sling.buscador.domain.port.out.KafkaProducerPort;
import com.java.hotel.sling.buscador.infrastructure.kafka.KafkaProducerAdapter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.java.hotel.sling.buscador.domain.model.HotelSearchMessage;
import org.springframework.kafka.core.KafkaTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BeanConfigTest {

    @Mock
    private KafkaProducerPort kafkaProducerPort;

    @Mock
    private KafkaTemplate<String, HotelSearchMessage> kafkaTemplate;

    @Test
    void searchUseCase_createsSearchServiceWithKafkaProducer() {
        BeanConfig beanConfig = new BeanConfig();
        
        SearchUseCase result = beanConfig.searchUseCase(kafkaProducerPort);

        assertNotNull(result);
        assertInstanceOf(SearchService.class, result);
    }

    @Test
    void kafkaProducerPort_createsKafkaProducerAdapter() {
        BeanConfig beanConfig = new BeanConfig();
        String topic = "test-topic";

        KafkaProducerPort result = beanConfig.kafkaProducerPort(kafkaTemplate, topic);

        assertNotNull(result);
        assertInstanceOf(KafkaProducerAdapter.class, result);
    }
}
