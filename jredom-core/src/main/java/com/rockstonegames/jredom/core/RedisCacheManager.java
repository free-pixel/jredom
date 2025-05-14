package com.rockstonegames.jredom.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;

@Component
public class RedisCacheManager {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private SerializationType serializationType = SerializationType.JSON;

    public enum SerializationType {
        JSON,
        PROTOBUF
    }

    public void setSerializationType(SerializationType serializationType) {
        this.serializationType = serializationType;
    }

    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public <T> void put(String key, T value, Duration ttl) {
        try {
            String json;
            if (serializationType == SerializationType.PROTOBUF && value instanceof Message) {
                // Process Protobuf message
                json = JsonFormat.printer().print((Message) value);
            } else {
                // Process java object
                json = objectMapper.writeValueAsString(value);
            }
            redisTemplate.opsForValue().set(key, json, ttl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T> Optional<T> get(String key, Class<T> clazz) {
        try {
            String json = redisTemplate.opsForValue().get(key);
            if (json == null) return Optional.empty();

            if (serializationType == SerializationType.PROTOBUF && Message.class.isAssignableFrom(clazz)) {
                // Process Protobuf message
                @SuppressWarnings("unchecked")
                Message.Builder builder = (Message.Builder) clazz.getMethod("newBuilder").invoke(null); // Get method signature
                JsonFormat.parser().merge(json, builder);
                @SuppressWarnings("unchecked")
                T message = (T) builder.build();
                return Optional.of(message);
            } else {
                // Process java object
                return Optional.of(objectMapper.readValue(json, clazz));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
