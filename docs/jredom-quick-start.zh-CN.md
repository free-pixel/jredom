# JRedom Core 使用说明

JRedom Core 是一个简单高效的 Redis ORM 解决方案，帮助您在 Java 应用中轻松管理 Redis 缓存。

## 快速开始

### 1. 添加依赖

在您的 Maven 项目中添加以下依赖：

```xml
<dependency>
  <groupId>com.rockstonegames.jredom</groupId>
  <artifactId>jredom-core</artifactId>
  <version>1.0.0</version>
</dependency>
```

### 2. 实体类准备

确保您的实体类实现 `EntityWithId` 接口：

```java
import com.rockstonegames.jredom.core.EntityWithId;

public class User implements EntityWithId<Long> {
    private Long id;
    private String name;
    private String email;
    
    // 构造函数、getter 和 setter
    
    @Override
    public Long getId() {
        return id;
    }
}
```

### 3. 使用注解方式

在服务方法上添加 `@RedisCache` 注解：

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

### 4. 使用函数式方式

也可以使用 `RedisOrmFunction` 进行更灵活的缓存操作：

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

### 5. 配置序列化方式

默认使用 JSON 序列化，也可以配置为使用 Protobuf：

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

## 更多信息

有关更详细的使用示例，请参考 `jredom-examples` 模块。


