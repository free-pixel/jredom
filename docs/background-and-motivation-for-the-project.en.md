

## Background and Motivation


Years ago, while I was working in game development, Redis started to become popular in the industry. Like in web development, Redis was mainly used as a business-level cache in games.

Before Redis, Memcached was commonly used, which only supports the `string` type and lacks the rich data structures Redis offers. To cache objects, developers either serialized them as JSON strings or binary data before storing them in Memcached.

In our project, we used JSON serialization for Java objects. Initially, this worked fine, but over time, the codebase became cluttered with `serObj()` and `descObj()` functions, which looked messy. Some suggested using reflection to unify the logic, but that wasn’t elegant either.

Later, we refactored the code and introduced Jackson for unified serialization. However, it still didn't reach the usability level of ORM frameworks like Hibernate or MyBatis. I kept thinking about creating a Redis ORM myself.

When Spring introduced RedisTemplate, it solved many of my needs, so the idea was put on hold.

Last year, AI-assisted programming became popular. I used tools like Cursor to build several projects. Recently, I finally had time to let AI help me develop a small version of the Redis ORM. It even generated complete test cases, and the whole process took less than an hour. That 1-hour “vibe coding” session fulfilled an idea I had been postponing for years and helped me regain the developer’s flow state.

There's a principle in vibe coding that I resonate with: **"Focus on solving the problem, not writing code."**

This project is not a traditional framework. It's a personal development experiment that reflects my thoughts on Redis ORM. I haven’t yet decided its future direction, but through AI and code, I’ve shared my idea with the open-source community.

I believe many developers may have similar ideas. Hopefully, this project will serve as a starting point for discussion and help us find a more elegant Redis ORM solution together.
