package com.rockstonegames.jredom.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CacheKeyGeneratorTest {

    @Test
    void testGenerateWithIntegerId() {
        // Given
        Class<?> userClass = DummyUser.class;
        Integer id = 123;

        // When
        String key = CacheKeyGenerator.generate(userClass, id);

        // Then
        assertEquals("dummyuser:id:123", key);
    }

    @Test
    void testGenerateWithStringId() {
        // Given
        Class<?> userClass = DummyUser.class;
        String id = "abc-123";

        // When
        String key = CacheKeyGenerator.generate(userClass, id);

        // Then
        assertEquals("dummyuser:id:abc-123", key);
    }

    @Test
    void testGenerateWithNullId() {
        // Given
        Class<?> userClass = DummyUser.class;

        // When
        String key = CacheKeyGenerator.generate(userClass, null);

        // Then
        assertEquals("dummyuser:id:null", key);
    }

    // Dummy class for testing
    private static class DummyUser {
        private Long id;
        private String name;
    }
}
