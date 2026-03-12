package com.java.hotel.sling.buscador.infrastructure.kafka;

import com.java.hotel.sling.buscador.domain.model.HotelSearchMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KafkaProducerAdapterTest {

    @Mock
    private KafkaTemplate<String, HotelSearchMessage> kafkaTemplate;

    private KafkaProducerAdapter kafkaProducerAdapter;

    @BeforeEach
    void setUp() {
        kafkaProducerAdapter = new KafkaProducerAdapter(kafkaTemplate, "hotel_availability_searches");
    }

    @Test
    void sendSearch_sendsMessageToKafka() {
        HotelSearchMessage message = new HotelSearchMessage(
            "test-id",
            "1234aBc",
            LocalDate.of(2023, 12, 29),
            LocalDate.of(2023, 12, 31),
            List.of(30, 29, 1, 3)
        );

        kafkaProducerAdapter.sendSearch(message);

        verify(kafkaTemplate, times(1)).send(
            eq("hotel_availability_searches"),
            eq("test-id"),
            eq(message)
        );
    }

    @Test
    void sendSearch_sendsWithCorrectTopicAndKey() {
        HotelSearchMessage message = new HotelSearchMessage(
            "search-uuid",
            "hotel-001",
            LocalDate.of(2024, 1, 1),
            LocalDate.of(2024, 1, 5),
            List.of(25, 30)
        );

        ArgumentCaptor<String> topicCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<HotelSearchMessage> messageCaptor = ArgumentCaptor.forClass(HotelSearchMessage.class);

        kafkaProducerAdapter.sendSearch(message);

        verify(kafkaTemplate).send(topicCaptor.capture(), keyCaptor.capture(), messageCaptor.capture());

        assertEquals("hotel_availability_searches", topicCaptor.getValue());
        assertEquals("search-uuid", keyCaptor.getValue());
        assertEquals("hotel-001", messageCaptor.getValue().hotelId());
    }
}
