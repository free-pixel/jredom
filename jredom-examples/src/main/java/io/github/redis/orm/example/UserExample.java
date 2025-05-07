package io.github.redis.orm.example;

import com.rockstonegames.jredom.core.RedisOrmFunction;
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
        // Using original pattern
        User user1 = new User(null, "john_doe", "john@example.com");
        user1 = userService.createUser(user1);
        System.out.println("Created user (original pattern): " + user1.getId());

        // Using new pattern with inner function
        User user2 = new User(null, "jane_doe", "jane@example.com");
        user2 = userService.createUser(user2, new RedisOrmFunction() {
          @Override
          public void execute(Object o) {
            // Custom Redis operations here
            System.out.println("Executing custom Redis operation");
          }
        });
        System.out.println("Created user (new pattern): " + user2.getId());

        // Update example with original pattern
        user1.setEmail("john.updated@example.com");
        userService.updateUser(user1);

        // Update example with new pattern
        user2.setEmail("jane.updated@example.com");
        userService.updateUser(user2, new RedisOrmFunction<User>() {
          @Override
          public void execute(User user) {
            // Custom Redis operations here
            System.out.println("Executing custom Redis update operation");
          }
        });

        // Delete examples
        userService.deleteUser(user1.getId()); // Original pattern
        userService.deleteUser(user2.getId(), new RedisOrmFunction<Long>() {
          @Override
          public void execute(Long aLong) {
            // Custom Redis delete operation
            System.out.println("Executing custom Redis delete operation");
          }
        });
    }
}
