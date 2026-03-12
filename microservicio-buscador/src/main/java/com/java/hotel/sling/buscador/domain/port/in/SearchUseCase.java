package com.java.hotel.sling.buscador.domain.port.in;

import com.java.hotel.sling.buscador.domain.model.HotelSearchRequest;
import com.java.hotel.sling.buscador.domain.model.SearchCreatedResponse;

public interface SearchUseCase {
    SearchCreatedResponse createSearch(HotelSearchRequest request);
}
