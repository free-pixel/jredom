package com.example.redisorm.core;

import com.example.redisorm.api.RedisOrmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
public class RedisOrmServiceImpl<T, ID> implements RedisOrmService<T, ID> {

    @Autowired
    private RedisCacheManager cacheManager;

    private final Class<T> entityClass;
    private final Duration cacheDuration = Duration.ofMinutes(30); // Default cache duration

    public RedisOrmServiceImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public T save(T entity) {
        String key = generateKey(getEntityId(entity));
        cacheManager.put(key, entity, cacheDuration);
        return entity;
    }

    @Override
    public void delete(ID id) {
        String key = generateKey(id);
        cacheManager.delete(key);
    }

    @Override
    public Optional<T> findById(ID id) {
        String key = generateKey(id);
        return cacheManager.get(key, entityClass);
    }

    @Override
    public void refresh(ID id) {
        String key = generateKey(id);
        cacheManager.delete(key);
    }

    private String generateKey(ID id) {
        return CacheKeyGenerator.generate(entityClass, id);
    }

    @SuppressWarnings("unchecked")
    private ID getEntityId(T entity) {
        // This is a simplified implementation. In a real application,
        // you would use reflection to get the ID field value
        if (entity instanceof EntityWithId) {
            return (ID) ((EntityWithId) entity).getId();
        }
        throw new IllegalArgumentException("Entity must implement EntityWithId interface");
    }
}