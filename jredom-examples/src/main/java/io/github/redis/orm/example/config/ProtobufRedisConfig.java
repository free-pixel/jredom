package io.github.redis.orm.example.config;

import com.rockstonegames.jredom.core.RedisCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class ProtobufRedisConfig {
    
    @Bean
    public RedisCacheManager protobufRedisCacheManager(RedisTemplate<String, String> redisTemplate) {
        RedisCacheManager manager = new RedisCacheManager();
        manager.setRedisTemplate(redisTemplate);
        manager.setSerializationType(RedisCacheManager.SerializationType.PROTOBUF);
        return manager;
    }
}