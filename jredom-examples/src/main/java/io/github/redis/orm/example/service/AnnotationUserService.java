package io.github.redis.orm.example.service;

import com.rockstonegames.jredom.annotation.CacheOperationType;
import com.rockstonegames.jredom.annotation.RedisCache;
import io.github.redis.orm.example.entity.User;
import io.github.redis.orm.example.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AnnotationUserService {

    @Autowired
    private UserMapper userMapper;

    @Transactional
    @RedisCache(operation = CacheOperationType.SAVE)
    public User createUser(User user) {
        userMapper.insert(user);
        return user;
    }

    @Transactional
    @RedisCache(operation = CacheOperationType.SAVE)
    public User updateUser(User user) {
        userMapper.update(user);
        return user;
    }

    @Transactional
    @RedisCache(operation = CacheOperationType.DELETE, idParamIndex = 0)
    public void deleteUser(Long id, Class<User> userClass) {
        userMapper.delete(id);
    }

    public User getUser(Long id) {
        // 先从Redis获取
        String key = com.rockstonegames.jredom.core.CacheKeyGenerator.generate(User.class, id);
        java.util.Optional<User> userFromCache = new com.rockstonegames.jredom.core.RedisCacheManager().get(key, User.class);
        
        if (userFromCache.isPresent()) {
            return userFromCache.get();
        }
        
        // 如果Redis中没有，从数据库获取并缓存
        User userFromDb = userMapper.findById(id);
        if (userFromDb != null) {
            saveToCache(userFromDb);
        }
        return userFromDb;
    }
    
    @RedisCache(operation = CacheOperationType.SAVE)
    private User saveToCache(User user) {
        return user;
    }
}