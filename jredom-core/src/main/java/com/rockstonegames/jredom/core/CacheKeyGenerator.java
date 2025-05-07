package com.rockstonegames.jredom.core;

public class CacheKeyGenerator {
    public static String generate(Class<?> clazz, Object key) {
        return clazz.getSimpleName().toLowerCase() + ":id:" + key;
    }
}
