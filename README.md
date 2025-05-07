
## About JRedom (Experimental / Beta)

[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square)](http://makeapullrequest.com)
![Last Commit](https://img.shields.io/github/last-commit/free-pixel/jredom)
![Open Issues](https://img.shields.io/github/issues/free-pixel/jredom)
![Pull Requests](https://img.shields.io/github/issues-pr/free-pixel/jredom)
![License](https://img.shields.io/github/license/free-pixel/jredom)
![Swift](https://img.shields.io/badge/built_with-Java-orange?logo=java)
![Platform](https://img.shields.io/badge/platform-Java-blue)
![GitHub Stars](https://img.shields.io/github/stars/free-pixel/jredom?style=social)


This Redis ORM solution is currently a temporary one. It is more of an implementation based on "convention." It is simple enough, flexible enough, and you can even directly copy the code without needing to include a jar file.


## Maybe the solution

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


## Background and Motivation

- [English](docs/background-and-motivation-for-the-project.en.md)
- [简体中文](docs/background-and-motivation-for-the-project.zh-CN.md)
- [繁體中文](docs/background-and-motivation-for-the-project.zh-Hant.md)
- [日本語](docs/background-and-motivation-for-the-project.ja.md)


## License

This project is licensed under the [MIT License](./LICENSE).  
Copyright (c) 2025 Free Pixel Games
