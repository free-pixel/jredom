package io.github.redis.orm.example.service;

import io.github.redis.orm.example.entity.User;
import io.github.redis.orm.example.mapper.UserMapper;
import com.rockstonegame.redisorm.core.RedisOrmServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserMapper userMapper;

    @Mock
    private RedisOrmServiceImpl<User, Long> redisOrmService;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User(1L, "test_user", "test@example.com");
        ReflectionTestUtils.setField(userService, "redisOrmService", redisOrmService);
    }

    @Test
    void createUser() {
        when(redisOrmService.save(any(User.class))).thenReturn(testUser);

        User result = userService.createUser(testUser);

        verify(userMapper).insert(testUser);
        verify(redisOrmService).save(testUser);
        assertEquals(testUser, result);
    }

    @Test
    void updateUser() {
        when(redisOrmService.save(any(User.class))).thenReturn(testUser);

        User result = userService.updateUser(testUser);

        verify(userMapper).update(testUser);
        verify(redisOrmService).save(testUser);
        assertEquals(testUser, result);
    }

    @Test
    void deleteUser() {
        userService.deleteUser(1L);

        verify(userMapper).delete(1L);
        verify(redisOrmService).delete(1L);
    }

    @Test
    void getUserFromCache() {
        when(redisOrmService.findById(1L)).thenReturn(Optional.of(testUser));

        User result = userService.getUser(1L);

        verify(redisOrmService).findById(1L);
        verify(userMapper, never()).findById(any());
        assertEquals(testUser, result);
    }

    @Test
    void getUserFromDatabase() {
        when(redisOrmService.findById(1L)).thenReturn(Optional.empty());
        when(userMapper.findById(1L)).thenReturn(testUser);

        User result = userService.getUser(1L);

        verify(redisOrmService).findById(1L);
        verify(userMapper).findById(1L);
        verify(redisOrmService).save(testUser);
        assertEquals(testUser, result);
    }
}