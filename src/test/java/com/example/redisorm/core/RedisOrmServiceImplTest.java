package com.example.redisorm.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Duration;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RedisOrmServiceImplTest {

    @Mock
    private RedisCacheManager cacheManager;

    private RedisOrmServiceImpl<TestEntity, Long> redisOrmService;

    @BeforeEach
    void setUp() {
        redisOrmService = new RedisOrmServiceImpl<>(TestEntity.class);
        ReflectionTestUtils.setField(redisOrmService, "cacheManager", cacheManager);
    }

    @Test
    void testSave() {
        // Given
        TestEntity entity = new TestEntity(1L, "Test Entity");
        String expectedKey = "testentity:id:1";

        // When
        TestEntity savedEntity = redisOrmService.save(entity);

        // Then
        verify(cacheManager).put(eq(expectedKey), eq(entity), any(Duration.class));
        assertEquals(entity, savedEntity);
    }

    @Test
    void testDelete() {
        // Given
        Long id = 1L;
        String expectedKey = "testentity:id:1";

        // When
        redisOrmService.delete(id);

        // Then
        verify(cacheManager).delete(expectedKey);
    }

    @Test
    void testFindById() {
        // Given
        Long id = 1L;
        TestEntity entity = new TestEntity(id, "Test Entity");
        String expectedKey = "testentity:id:1";
        when(cacheManager.get(expectedKey, TestEntity.class)).thenReturn(Optional.of(entity));

        // When
        Optional<TestEntity> result = redisOrmService.findById(id);

        // Then
        assertTrue(result.isPresent());
        assertEquals(entity, result.get());
    }

    @Test
    void testRefresh() {
        // Given
        Long id = 1L;
        String expectedKey = "testentity:id:1";

        // When
        redisOrmService.refresh(id);

        // Then
        verify(cacheManager).delete(expectedKey);
    }

    // Test Entity class
    private static class TestEntity implements EntityWithId<Long> {
        private final Long id;
        private final String name;

        public TestEntity(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}