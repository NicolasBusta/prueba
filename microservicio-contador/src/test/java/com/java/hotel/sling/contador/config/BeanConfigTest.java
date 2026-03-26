package com.java.hotel.sling.contador.config;

import com.java.hotel.sling.contador.application.CountService;
import com.java.hotel.sling.contador.domain.port.in.CountUseCase;
import com.java.hotel.sling.contador.domain.port.out.SearchRepositoryPort;
import com.java.hotel.sling.contador.infrastructure.persistence.SearchAvailabilityRepository;
import com.java.hotel.sling.contador.infrastructure.persistence.SearchRepositoryAdapter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BeanConfigTest {

    @Mock
    private SearchAvailabilityRepository repository;

    @Test
    void searchRepositoryPort_createsSearchRepositoryAdapter() {
        BeanConfig beanConfig = new BeanConfig();

        SearchRepositoryPort result = beanConfig.searchRepositoryPort(repository);

        assertNotNull(result);
        assertInstanceOf(SearchRepositoryAdapter.class, result);
    }

    @Test
    void countUseCase_createsCountServiceWithRepository() {
        BeanConfig beanConfig = new BeanConfig();
        SearchRepositoryPort repositoryPort = mock(SearchRepositoryPort.class);

        CountUseCase result = beanConfig.countUseCase(repositoryPort);

        assertNotNull(result);
        assertInstanceOf(CountService.class, result);
    }
}
