package io.github.redis.orm.example.service;

import com.rockstonegames.jredom.core.CacheKeyGenerator;
import com.rockstonegames.jredom.core.RedisCacheManager;
import io.github.redis.orm.example.mapper.UserMapper;
import io.github.redis.orm.example.proto.UserProto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Optional;

@Service
public class ProtobufUserService {

    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    @Qualifier("protobufRedisCacheManager")
    private RedisCacheManager redisCacheManager;
    
    @Transactional
    public UserProto createUser(UserProto userProto) {
        // 1. 转换为实体对象并保存到MySQL
        io.github.redis.orm.example.entity.User user = new io.github.redis.orm.example.entity.User(
                null, 
                userProto.getName(), 
                userProto.getEmail()
        );
        userMapper.insert(user);
        
        // 2. 创建带ID的Protobuf对象
        UserProto savedProto = UserProto.newBuilder()
                .setId(user.getId())
                .setName(user.getName())
                .setEmail(user.getEmail())
                .build();
        
        // 3. 保存到Redis
        String key = CacheKeyGenerator.generate(UserProto.class, savedProto.getId());
        redisCacheManager.put(key, savedProto, Duration.ofMinutes(30));
        
        return savedProto;
    }
    
    @Transactional
    public UserProto updateUser(UserProto userProto) {
        // 1. 转换为实体对象并更新MySQL
        io.github.redis.orm.example.entity.User user = new io.github.redis.orm.example.entity.User(
                userProto.getId(), 
                userProto.getName(), 
                userProto.getEmail()
        );
        userMapper.update(user);
        
        // 2. 更新Redis
        String key = CacheKeyGenerator.generate(UserProto.class, userProto.getId());
        redisCacheManager.put(key, userProto, Duration.ofMinutes(30));
        
        return userProto;
    }
    
    @Transactional
    public void deleteUser(Long id) {
        // 1. 从MySQL删除
        userMapper.delete(id);
        
        // 2. 从Redis删除
        String key = CacheKeyGenerator.generate(UserProto.class, id);
        redisCacheManager.delete(key);
    }
    
    public UserProto getUser(Long id) {
        // 1. 尝试从Redis获取
        String key = CacheKeyGenerator.generate(UserProto.class, id);
        Optional<UserProto> userFromCache = redisCacheManager.get(key, UserProto.class);
        
        if (userFromCache.isPresent()) {
            return userFromCache.get();
        }
        
        // 2. 如果Redis中没有，从数据库获取并缓存
        io.github.redis.orm.example.entity.User userFromDb = userMapper.findById(id);
        if (userFromDb != null) {
            UserProto userProto = UserProto.newBuilder()
                    .setId(userFromDb.getId())
                    .setName(userFromDb.getName())
                    .setEmail(userFromDb.getEmail())
                    .build();
            
            redisCacheManager.put(key, userProto, Duration.ofMinutes(30));
            return userProto;
        }
        
        return null;
    }
}