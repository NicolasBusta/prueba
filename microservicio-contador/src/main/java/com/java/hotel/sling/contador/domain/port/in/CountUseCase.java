package com.java.hotel.sling.contador.domain.port.in;

import com.java.hotel.sling.contador.domain.model.CountResponse;

public interface CountUseCase {
    CountResponse getCountBySearchId(String searchId);
}
