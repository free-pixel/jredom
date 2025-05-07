package io.github.redis.orm.example.service;

import io.github.redis.orm.example.entity.User;
import io.github.redis.orm.example.mapper.UserMapper;
import com.rockstonegame.redisorm.core.RedisOrmFunction;
import com.rockstonegame.redisorm.core.RedisOrmServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private RedisOrmServiceImpl<User, Long> redisOrmService;

    public UserService() {
        this.redisOrmService = new RedisOrmServiceImpl<>(User.class);
    }

    // Original pattern
    @Transactional
    public User createUser(User user) {
        // 1. Save to MySQL
        userMapper.insert(user);
        // 2. Save to Redis
        return redisOrmService.save(user);
    }

    // New pattern with inner function
    @Transactional
    public User createUser(User user, RedisOrmFunction redisOrmFunction) {
        // 1. Save to MySQL
        userMapper.insert(user);
        
        // 2. Execute custom Redis operation
        redisOrmFunction.execute();
        
        return user;
    }

    // Original pattern
    @Transactional
    public User updateUser(User user) {
        // 1. Update MySQL
        userMapper.update(user);
        // 2. Update Redis
        return redisOrmService.save(user);
    }

    // New pattern with inner function
    @Transactional
    public User updateUser(User user, RedisOrmFunction redisOrmFunction) {
        // 1. Update MySQL
        userMapper.update(user);
        
        // 2. Execute custom Redis operation
        redisOrmFunction.execute();
        
        return user;
    }

    // Original pattern
    @Transactional
    public void deleteUser(Long id) {
        // 1. Delete from MySQL
        userMapper.delete(id);
        // 2. Delete from Redis
        redisOrmService.delete(id);
    }

    // New pattern with inner function
    @Transactional
    public void deleteUser(Long id, RedisOrmFunction redisOrmFunction) {
        // 1. Delete from MySQL
        userMapper.delete(id);
        
        // 2. Execute custom Redis operation
        redisOrmFunction.execute();
    }

    public User getUser(Long id) {
        // 1. Try to get from Redis first
        Optional<User> userFromCache = redisOrmService.findById(id);
        if (userFromCache.isPresent()) {
            return userFromCache.get();
        }
        
        // 2. If not in Redis, get from MySQL and cache it
        User userFromDb = userMapper.findById(id);
        if (userFromDb != null) {
            redisOrmService.save(userFromDb);
        }
        return userFromDb;
    }
}
