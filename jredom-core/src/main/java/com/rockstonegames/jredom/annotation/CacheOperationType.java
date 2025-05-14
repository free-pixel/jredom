package com.rockstonegames.jredom.annotation;

/**
 * Redis缓存操作类型
 */
public enum CacheOperationType {
    /**
     * 保存或更新缓存
     */
    SAVE,

    /**
     * 删除缓存
     */
    DELETE,

    /**
     * 刷新缓存
     */
    REFRESH
}