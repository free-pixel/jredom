package com.rockstonegames.jredom.core;

@FunctionalInterface
public interface RedisOrmFunction<T> {
    void execute(T t);
}
