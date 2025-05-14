package io.github.redis.orm.example;

import io.github.redis.orm.example.proto.UserProto;
import io.github.redis.orm.example.service.ProtobufUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProtobufExample {

    @Autowired
    private ProtobufUserService protobufUserService;

    public static void main(String[] args) {
        SpringApplication.run(ProtobufExample.class, args);
    }

    @Bean
    public CommandLineRunner protobufRunner() {
        return args -> {
            System.out.println("=== 使用Protobuf的Redis ORM示例 ===");

            // 创建用户
            UserProto user = UserProto.newBuilder()
                    .setName("protobuf_user")
                    .setEmail("protobuf@example.com")
                    .build();

            UserProto createdUser = protobufUserService.createUser(user);
            System.out.println("创建用户 (Protobuf): " + createdUser.getId());

            // 更新用户
            UserProto updatedUser = UserProto.newBuilder(createdUser)
                    .setEmail("protobuf.updated@example.com")
                    .build();

            protobufUserService.updateUser(updatedUser);
            System.out.println("更新用户 (Protobuf)");

            // 获取用户
            UserProto retrievedUser = protobufUserService.getUser(createdUser.getId());
            System.out.println("获取用户 (Protobuf): " + retrievedUser.getEmail());

            // 删除用户
            protobufUserService.deleteUser(createdUser.getId());
            System.out.println("删除用户 (Protobuf)");
        };
    }
}