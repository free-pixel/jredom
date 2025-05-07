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
        // 获取方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        
        // 获取注解
        RedisCache redisCache = method.getAnnotation(RedisCache.class);
        
        // 获取参数
        Object[] args = joinPoint.getArgs();
        
        // 执行原方法
        Object result = joinPoint.proceed();
        
        // 根据操作类型处理缓存
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
            // 需要知道实体类型，这里简化处理
            if (args.length > idParamIndex + 1 && args[idParamIndex + 1] instanceof Class) {
                Class<?> entityClass = (Class<?>) args[idParamIndex + 1];
                String key = com.rockstonegames.jredom.core.CacheKeyGenerator.generate(
                        entityClass, id);
                cacheManager.delete(key);
            }
        }
    }
    
    private void handleRefreshOperation(Object[] args, int idParamIndex) {
        // 刷新操作实际上就是删除缓存
        handleDeleteOperation(args, idParamIndex);
    }
}