package io.github.redis.orm.example.entity;

import com.rockstonegames.jredom.core.EntityWithId;

public class User implements EntityWithId<Long> {
    private Long id;
    private String name;  // Ensure the property name is 'name' instead of 'username'
    private String email;

    public User() {
    }

    public User(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
