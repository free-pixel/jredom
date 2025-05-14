package com.rockstonegames.jredom.aop;

import com.rockstonegames.jredom.annotation.CacheOperationType;
import com.rockstonegames.jredom.annotation.RedisCache;
import com.rockstonegames.jredom.core.EntityWithId;
import com.rockstonegames.jredom.core.RedisCacheManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.Duration;

@Aspect
@Component
public class RedisCacheAspect {

    @Autowired
    private RedisCacheManager cacheManager;

    @Around("@annotation(com.rockstonegames.jredom.annotation.RedisCache)")
    public Object handleRedisCache(ProceedingJoinPoint joinPoint) throws Throwable {
        // Get method signature
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        // Get method signature
        RedisCache redisCache = method.getAnnotation(RedisCache.class);

        // Get method args
        Object[] args = joinPoint.getArgs();

        // Get method invoked result
        Object result = joinPoint.proceed();

        // process cache operation
        switch (redisCache.operation()) {
            case SAVE:
                handleSaveOperation(result, redisCache.expireMinutes());
                break;
            case DELETE:
                handleDeleteOperation(args, redisCache.idParamIndex());
                break;
            case REFRESH:
                handleRefreshOperation(args, redisCache.idParamIndex());
                break;
        }

        return result;
    }

    private void handleSaveOperation(Object result, int expireMinutes) {
        if (result instanceof EntityWithId) {
            EntityWithId<?> entity = (EntityWithId<?>) result;
            String key = com.rockstonegames.jredom.core.CacheKeyGenerator.generate(
                    entity.getClass(), entity.getId());
            cacheManager.put(key, entity, Duration.ofMinutes(expireMinutes));
        }
    }

    private void handleDeleteOperation(Object[] args, int idParamIndex) {
        if (args.length > idParamIndex) {
            Object id = args[idParamIndex];
            // Need to know entity type, simplified handling here
            if (args.length > idParamIndex + 1 && args[idParamIndex + 1] instanceof Class) {
                Class<?> entityClass = (Class<?>) args[idParamIndex + 1];
                String key = com.rockstonegames.jredom.core.CacheKeyGenerator.generate(
                        entityClass, id);
                cacheManager.delete(key);
            }
        }
    }

    private void handleRefreshOperation(Object[] args, int idParamIndex) {
        // Refresh operation is essentially a cache deletion
        handleDeleteOperation(args, idParamIndex);
    }
}