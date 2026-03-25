package com.java.hotel.sling.contador.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchAvailabilityRepository extends JpaRepository<SearchAvailabilityEntity, String> {
}
