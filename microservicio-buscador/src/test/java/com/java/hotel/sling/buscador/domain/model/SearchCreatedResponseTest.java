package com.java.hotel.sling.buscador.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SearchCreatedResponseTest {

    @Test
    void constructor_createsResponseWithSearchId() {
        SearchCreatedResponse response = new SearchCreatedResponse("test-uuid-123");

        assertEquals("test-uuid-123", response.searchId());
    }

    @Test
    void equals_sameSearchId_returnsTrue() {
        SearchCreatedResponse response1 = new SearchCreatedResponse("test-uuid");
        SearchCreatedResponse response2 = new SearchCreatedResponse("test-uuid");

        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());
    }

    @Test
    void equals_differentSearchId_returnsFalse() {
        SearchCreatedResponse response1 = new SearchCreatedResponse("uuid-1");
        SearchCreatedResponse response2 = new SearchCreatedResponse("uuid-2");

        assertNotEquals(response1, response2);
    }

    @Test
    void toString_containsSearchId() {
        SearchCreatedResponse response = new SearchCreatedResponse("test-uuid");

        String result = response.toString();

        assertTrue(result.contains("test-uuid"));
    }
}
