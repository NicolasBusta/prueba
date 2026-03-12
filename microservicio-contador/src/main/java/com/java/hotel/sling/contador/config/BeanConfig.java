package com.java.hotel.sling.contador.config;

import com.java.hotel.sling.contador.application.CountService;
import com.java.hotel.sling.contador.domain.port.in.CountUseCase;
import com.java.hotel.sling.contador.domain.port.out.SearchRepositoryPort;
import com.java.hotel.sling.contador.infrastructure.persistence.SearchRepositoryAdapter;
import com.java.hotel.sling.contador.infrastructure.persistence.SearchAvailabilityRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public SearchRepositoryPort searchRepositoryPort(SearchAvailabilityRepository repository) {
        return new SearchRepositoryAdapter(repository);
    }

    @Bean
    public CountUseCase countUseCase(SearchRepositoryPort repositoryPort) {
        return new CountService(repositoryPort);
    }
}
