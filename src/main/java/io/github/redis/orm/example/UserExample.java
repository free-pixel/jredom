package io.github.redis.orm.example;

import io.github.redis.orm.example.entity.User;
import io.github.redis.orm.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserExample implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) {
        // Create a new user
        User user = new User(null, "john_doe", "john@example.com");
        user = userService.createUser(user);
        System.out.println("Created user: " + user.getId());

        // Get user (will be from Redis)
        User cachedUser = userService.getUser(user.getId());
        System.out.println("Found user in cache: " + cachedUser.getUsername());

        // Update user
        cachedUser.setEmail("john.doe@example.com");
        userService.updateUser(cachedUser);
        System.out.println("Updated user email");

        // Delete user
        userService.deleteUser(user.getId());
        System.out.println("Deleted user");
    }
}