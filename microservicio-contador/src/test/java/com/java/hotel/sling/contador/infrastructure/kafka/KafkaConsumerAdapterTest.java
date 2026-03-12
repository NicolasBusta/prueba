package com.java.hotel.sling.contador.infrastructure.kafka;

import com.java.hotel.sling.contador.domain.model.HotelSearchMessage;
import com.java.hotel.sling.contador.domain.port.out.SearchRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KafkaConsumerAdapterTest {

    @Mock
    private SearchRepositoryPort repositoryPort;

    private KafkaConsumerAdapter kafkaConsumerAdapter;

    @BeforeEach
    void setUp() {
        kafkaConsumerAdapter = new KafkaConsumerAdapter(repositoryPort);
    }

    @Test
    void consumeSearch_validMessage_persistsToDatabase() {
        HotelSearchMessage message = new HotelSearchMessage(
            "test-uuid",
            "1234aBc",
            LocalDate.of(2023, 12, 29),
            LocalDate.of(2023, 12, 31),
            List.of(30, 29, 1, 3)
        );

        kafkaConsumerAdapter.consumeSearch(message);

        ArgumentCaptor<HotelSearchMessage> messageCaptor = ArgumentCaptor.forClass(HotelSearchMessage.class);
        verify(repositoryPort, times(1)).save(messageCaptor.capture());

        HotelSearchMessage savedMessage = messageCaptor.getValue();
        assertEquals("test-uuid", savedMessage.searchId());
        assertEquals("1234aBc", savedMessage.hotelId());
    }

    @Test
    void consumeSearch_handlesException() {
        HotelSearchMessage message = new HotelSearchMessage(
            "test-uuid",
            "1234aBc",
            LocalDate.of(2023, 12, 29),
            LocalDate.of(2023, 12, 31),
            List.of(30, 29)
        );

        doThrow(new RuntimeException("DB error")).when(repositoryPort).save(any());

        assertDoesNotThrow(() -> kafkaConsumerAdapter.consumeSearch(message));
    }
}
