package io.github.redis.orm.example.entity;

import com.example.redisorm.core.EntityWithId;

public class User implements EntityWithId<Long> {
    private Long id;
    private String username;
    private String email;
    
    public User() {}
    
    public User(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}