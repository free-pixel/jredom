package com.rockstonegames.jredom.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记需要同步到Redis缓存的方法
 * 当标记的方法被调用时，会自动同步数据到Redis
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface RedisCache {
    /**
     * 缓存操作类型
     */
    CacheOperationType operation() default CacheOperationType.SAVE;
    
    /**
     * 实体ID的参数索引，默认为0
     */
    int idParamIndex() default 0;
    
    /**
     * 缓存过期时间（分钟）
     */
    int expireMinutes() default 30;
}