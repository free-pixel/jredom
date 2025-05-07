
# JRedom Core User Guide

JRedom Core is a simple and efficient Redis ORM solution that helps you manage Redis caching easily within Java applications.

## Quick Start

### 1. Add Dependency

Add the following dependency to your Maven project:

```xml
<dependency>
  <groupId>com.rockstonegames.jredom</groupId>
  <artifactId>jredom-core</artifactId>
  <version>1.0.0</version>
</dependency>
```

### 2. Prepare Your Entity Class

Make sure your entity class implements the `EntityWithId` interface:

```java
import com.rockstonegames.jredom.core.EntityWithId;

public class User implements EntityWithId<Long> {
    private Long id;
    private String name;
    private String email;

    // Constructor, getters, and setters

    @Override
    public Long getId() {
        return id;
    }
}
```

### 3. Annotation-Based Usage

Add the `@RedisCache` annotation to your service methods:

```java
@Service
public class UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Transactional
    @RedisCache(operation = CacheOperationType.SAVE)
    public User createUser(User user) {
        userMapper.insert(user);
        return user;
    }
    
    @Transactional
    @RedisCache(operation = CacheOperationType.DELETE, idParamIndex = 0)
    public void deleteUser(Long id) {
        userMapper.delete(id);
    }
}
```

### 4. Functional Usage

You can also use `RedisOrmFunction` for more flexible cache operations:

```java
@Service
public class UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private RedisOrmServiceImpl<User, Long> redisOrmService;
    
    @Transactional
    public User createUser(User user, RedisOrmFunction<User> redisFunction) {
        userMapper.insert(user);
        redisFunction.execute(user);
        return user;
    }
}
```

### 5. Configure Serialization

JSON serialization is used by default. You can switch to Protobuf:

```java
@Configuration
public class RedisConfig {
    @Bean
    public RedisCacheManager redisCacheManager(RedisTemplate<String, String> redisTemplate) {
        RedisCacheManager manager = new RedisCacheManager();
        manager.setSerializationType(RedisCacheManager.SerializationType.PROTOBUF);
        return manager;
    }
}
```

## More Information

For more detailed usage examples, refer to the `jredom-examples` module.

