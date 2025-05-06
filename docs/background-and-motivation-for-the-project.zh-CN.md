

## 项目产生的背景与动机

- [简体中文 🇨🇳](background-and-motivation-for-the-project.zh-CN.md)
- [English 🇺🇸](background-and-motivation-for-the-project.en.md)
- [繁體中文 🇹🇼](background-and-motivation-for-the-project.zh-Hant.md)
- [日本語 🇯🇵](background-and-motivation-for-the-project.ja.md)


多年前，我在做游戏开发，redis刚才游戏行业流行起来。和互联网行业应用一样， redis在游戏行业也是
做为业务缓存使用。

在使用redis之前，游戏行业普遍使用 memcached 来做业务缓存，mc 没有 redis 这么丰富的数据结构，
mc 只支持 string 类型，因此在缓存对象数据时有两种方 案一是将对象序列化成 json string，然后
set 到 mc 中；另一种是将对象序列化成二进制数据，然后set到mc中。

在当时的项目中，我们也采用了类似 json string 的方案，将 java 对象序列化 为 json，用的时候再
将 string 反序列化为 java 对象。这样项目初期，感觉还能 使用，但是随着代码量的积累，代码里充斥
着很多 serObj(), descObj() 的代码，很多代码看着很丑陋。当然也有很多人建议，你可以使用反射将该
方法统一起来。

后来再进行代码重构的时候，我们也使用了jackson来统一管理了这部分代码的序列化 和反序列化。但是，这
没有达到我们像使用 hibernate 和 mybatis 等 orm 框架等易用层度。

因此，为 redis 提供一个 orm 框架，就一直在我的想法里思考着，后来 spring 推出了 redis template，
在很大程度上满足了我对 redis orm 使用的诉求。因此自研一个 redis orm 框架的想法就一直搁置着。

去年开始流行起了ai编程，我也使用cursor等工具落地了一些项目，最近有时间刚好把redis orm的这个项
目让ai帮我开发了一个小的版本。我又让它帮我添加了完整的测试用例，整个过程完成花了不到1小时。就这
么1小时的 vibe coding，满足了我好多年想做而一直没有时间落地的一个方案。vibe coding 也能让开
发者找回心流的编程状态。

vibe coding 有一条原则：要专注于解决问题，而不是代码。这句话刚好能反应我当前的状态。

使用 ai 编程时，我给了他4种方案，每种方案我都想尝试下。这个项目不能算是一个传统的框架或者方案。
这可以看成是我对实现 redis orm 的一种个人开发实验。

我也没想好最终该项目的一个演进方向，但是我把我的想法通过代码的形式，借助 ai 的力量，将其完整的展
现在了开源社区。我想会有很多开发者也有类似的想法，可能也在寻找一类似解决方案。因此，也希望通过该
项目能交流对寻找一种更优雅的 redis orm 的解决方案。


