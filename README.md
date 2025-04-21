


1、basicly implementation a simple redis orm lib




our goal is implement one redis orm lib, when you change your db data, the redis
case sync at once, at the same time keep the code simple and clean.




there are three plan to slove the problem

1、use event driver, the event framework we use spring-event, when database changed, the change method send one event notify the redis listener to sync redis

2、make redis-orm as a annotion, when db method invoked, the orm code at time work,but i don't like to use annotation, it make the code understand complex.

3、use mybatis interceptor, when db update param equals EntityWithId, we can invoke the sync code

4、use inner function, like the mask code :

```java

    @Transactional
    public User updateUser(User user, function funcRedisOrm) {

        // 1. Update MySQL
        userMapper.update(user);
        
        // 2. Update Redis
        funcRedisOrm();

        return use;
    }

```

this plan like use the proxy pattern to solve the problem.


summary: the only problem is how to control the invoke time, and the invoke mechanism.


synchronous call ；synchronized call 。




