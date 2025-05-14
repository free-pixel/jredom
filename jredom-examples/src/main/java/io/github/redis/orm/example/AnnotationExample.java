package io.github.redis.orm.example;

import io.github.redis.orm.example.entity.User;
import io.github.redis.orm.example.service.AnnotationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AnnotationExample implements CommandLineRunner {

    @Autowired
    private AnnotationUserService annotationUserService;

    @Override
    public void run(String... args) {
        System.out.println("=== 使用注解方式的Redis ORM示例 ===");

        // 创建用户
        User user = new User(null, "annotation_user", "annotation@example.com");
        user = annotationUserService.createUser(user);
        System.out.println("创建用户 (注解方式): " + user.getId());

        // 更新用户
        user.setEmail("annotation.updated@example.com");
        annotationUserService.updateUser(user);
        System.out.println("更新用户 (注解方式)");

        // 获取用户
        User retrievedUser = annotationUserService.getUser(user.getId());
        System.out.println("获取用户 (注解方式): " + retrievedUser.getEmail());

        // 删除用户
        annotationUserService.deleteUser(user.getId(), User.class);
        System.out.println("删除用户 (注解方式)");
    }
}