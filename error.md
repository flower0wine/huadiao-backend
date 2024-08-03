# 遇到的问题

这个 MD 文件编写的比较晚了，之前遇到的很多问题都没有记录下来，开发过程中遇到的问题
基本都是靠脑子记住，或者是记在电脑上的某个文件当中，用 MD 记录，还有通过发布文章的
的方式让其他人知道，问题记录比较分散。

下面开始将记录迄今为止已记录的所有问题，将会以文字描述或者是文章链接的形式给出。

## 1. 服务器启动时遇到 404

### 1.1. properties 文件加载错误

这个问题是我在部署的时候冒出来的，我在本地跑并没有这个问题。

问题的原因是运行环境没有更改，因为我为代码设置两种环境，一个是 dev（开发环境），
一个是 prod（生产环境）。由于 spring 对环境的切换没有一个统一的处理方案，所以我
在 `com/huadiao/configuration/DevConfig.java` 和 `com/huadiao/configuration/ProdConfig.java`
上加上了一个 `@Profile` 注解，这个注解貌似并没有作用，所以我采用了定义环境变量的
方式。就是在 `src/main/webapp/WEB-INF/web.xml` 文件中书写了下面的代码：

```xml
<!-- 激活的开发环境 -->
<context-param>
    <param-name>spring.profiles.active</param-name>
    <param-value>prod</param-value>
</context-param>
```

这个变量会被 `spring` 识别，然后与 `@Profile` 注解联合发挥作用。

所以最终的问题就是我在部署时，忘记将 dev 改成 prod 了，还有一些 properties
属性忘记定义了，导致 spring 没有加载这些属性。