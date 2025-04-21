package com.example.redisorm.api;
import java.util.Optional;

public interface RedisOrmService<T, ID> {
    T save(T entity);
    void delete(ID id);
    Optional<T> findById(ID id);
    void refresh(ID id);
}