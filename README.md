# SpringCloud 实战篇-入门项目构建

构建环境
> Java 8 
> Spring Boot  	 2.2.2.RELEASE
> Spring Cloud 	Hoxton.RELEASE

[GitHub地址](https://github.com/leewf/spring-cloud-quick-start-demo)
[原文地址][http://leewf.xyz/Java/2020/01/SpringCloud-actual-0.html]

## 基础骨架构建


### 工程构建

基础骨架最终目录结构如下：

```java
└── cloud-main：主工程
    └ server-a ：测试子模块a
    └ server-b ：测试子模块b
```

#### 创建主工程

项目命名：`cloud-main`

创建流程：创建一个`Maven`项目作为主工程，类型这里建议使用`maven-archetype-quickstart`骨架，创建过程如下：

- `File-->New-->Project`
- `-->Maven-->Create from archetype-->maven-archetype-quickstart-Next`
- `-->GroupId={你的GroupId}-->AritifactId={你的ArtifactId}`
- `-->Next-->Next-->Finish-->New Whindow`

#### 构建子模块

项目命名：`server-a`\`server-b`

创建流程：子模块项目创建于主工程之内，创建过程如下：

- `右键点击项目名称-->New-->Module`
- `选中Spring Initializr-->Next`
- `-->Group={主工程的GroupId}-->Aritifact={当前模块的ArtifactId}`、
- `-->Next-->Next-->Finish`


### pom.xml配置

#### **主工程pom.xml 配置**

主工程的`pom.xml`文件中，主要做以下几件事：

- 配置`Spring Boot`的版本
- 配置`Spring Cloud`的版本
- 配置微服务的一些基本组件，如`actuator`、`eureka`、`web`和`test`。
- 配置编码方式
- 配置`java`版本
- 配置子模块


```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>cloud.wf</groupId>
    <artifactId>cloud-main</artifactId>
    <version>1.0</version>
    <name>cloud-main</name>
    <!-- FIXME change it to the project's website -->
    <url>http://www.example.com</url>

    <!--子模块工厂配置-->
    <modules>
        <module>server-a</module>
        <module>server-b</module>
    </modules>

    <!--Spring Boot配置-->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.2.2.RELEASE</version>
    </parent>

    <!--配置参数-->
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
        <spring-cloud.version>Dalston.SR1</spring-cloud.version>
    </properties>

    <!--依赖组件配置-->
    <dependencies>
        <!--Spring Boot 执行器组件-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <!--Spring Boot Web组件-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!--Spring Boot 测试组件-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <!--Spring Cloud 版本序列配置-->
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <repositories>
        <repository>
            <id>spring-snapshots</id>
            <name>Spring Snapshots</name>
            <url>https://repo.spring.io/snapshot</url>
            <snapshots>
                <enabled>true</enabled>
            </snapshots>
        </repository>
        <repository>
            <id>spring-milestones</id>
            <name>Spring Milestones</name>
            <url>https://repo.spring.io/milestone</url>
            <snapshots>
                <enabled>false</enabled>
            </snapshots>
        </repository>
    </repositories>

</project>
```
#### **子模块pom.xml 配置**

子模块`pom.xml`可以引用主工程`pom.xml`的依赖关系，所以也对其进行配置，其中`module-a`的`pom.xml`配置如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>cloud.wf</groupId>
    <artifactId>server-a</artifactId>
    <version>0.0.1</version>
    <packaging>jar</packaging>
    <name>module-a</name>
    <description>Demo project for Spring Boot</description>

    <!--父工程的依赖-->
    <parent>
        <groupId>cloud.wf</groupId>
        <artifactId>cloud-main</artifactId>
        <version>1.0</version>
        <relativePath/>
    </parent>

    <properties>
        <java.version>1.8</java.version>
    </properties>

    <!--子模块的个性化依赖-->
    <dependencies>
    </dependencies>

</project>
```

`module-a`从`主工程`中继承了一系列依赖关系，如`actuator`、`eureka`、`web`和`test`等组件。

### 测试运行

子模块`server-a`

```java
@RestController
public class TestController {
    Logger logger = LoggerFactory.getLogger(TestController.class);

    @GetMapping("test")
    public String test(){
        logger.info("hello,module-a test");
        return "hello,module-a test";
    }
}
```

**application.yml**

```yml
server:
  port: 8101
```

**测试链接：**http://localhost:8101/test



### **遇到问题**

#### Spring Boot 和Spring Cloud 版本兼容问题

**项目启动错误信息**

```java
java.lang.NoSuchMethodError: org.springframework.boot.builder.SpringApplicationBuilder.<init>
at org.springframework.cloud.bootstrap.BootstrapApplicationListener…………
```

**版本匹配关系**

官方介绍：[Spring.IO - Spring Cloud ](https://spring.io/projects/spring-cloud)

| Cloud Version | Boot Version |
| ------------- | ------------ |
| Hoxton        | 2.2.x        |
| Greenwich     | 2.1.x        |
| Finchley      | 2.0.x        |
| Edgware       | 1.5.x        |
| Dalston       | 1.5.x        |


## Eureka 服务治理

**基本了解**

`Eureka `负责“服务注册，服务发现”等工作。分为 `Eureka Server `和 `Eureka Client`

详细了解：
[Spring Cloud Eureka详解](https://blog.csdn.net/sunhuiliang85/article/details/76222517)
[spring.io - eureka](https://cloud.spring.io/spring-cloud-netflix/reference/html/#service-discovery-eureka-clients)
[spring cloud 学习笔记-Eureka](https://blog.csdn.net/zzp448561636/article/details/70198878)

项目最终结构及角色分配如下：
```java
└── cloud-main：主工程
	└ eureka-a ：注册中心a
	|
    └ server-a ：服务提供者a(eureka client)
    └ server-b ：服务提供者b(eureka client)
```

### Eureka Server 实现

#### Eureka Server 模块构建
项目命名：`eureka-a`

创建流程：`Eureka Server`项目创建于主工程之内，创建过程如下：

- `右键点击项目名称-->New-->Module`
- `选中Spring Initializr-->Next`
- `-->Group={主工程的GroupId}-->Aritifact={当前模块的ArtifactId}`、
- `-->Next-->Next-->Finish`

#### 相关配置

官方文档：[spring.io - eureka-server-and-client-starters](https://cloud.spring.io/spring-cloud-netflix/reference/html/#disabling-ribbon-with-eureka-server-and-client-starters)

目的：

1、`pom.xml`中引入服务注册组件

2、配置`eureka`客户端及服务端
##### pom.xml 配置
###### 主工程配置

目的：
1、配置`eureka-a`为主工程子模块
2、引入服务注册组件

```xml
    <!--子模块工厂配置-->
    <modules>
        <!--1、新增子模块-->
        <module>eureka-a</module>

        <module>server-a</module>
        <module>server-b</module>
    </modules>

    <!--依赖组件配置-->
    <dependencies>
         <!--2、新增 Spring Cloud 服务注册组件 包含客户端-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
            <!--
            For details please see：
            https://cloud.spring.io/spring-cloud-netflix/reference/html/#disabling-ribbon-with-eureka-server-and-client-starters
            -->
            <exclusions>
                <exclusion>
                    <groupId>org.springframework.cloud</groupId>
                    <artifactId>spring-cloud-starter-ribbon</artifactId>
                </exclusion>
                <exclusion>
                    <groupId>com.netflix.ribbon</groupId>
                    <artifactId>ribbon-eureka</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
    </dependencies>
```

###### eureka 模块配置

目的：

1、沿用主工程配置：由于主工程中已经引入`eureka-server`模块，所以子模块只需引入主工程配置即可。

```xml
 <parent>
        <groupId>cloud.wf</groupId>
        <artifactId>cloud-main</artifactId>
        <version>1.0</version>
        <relativePath/>
  </parent>
```


##### application.yml 配置

目的：

1、配置注册中心参数

```yml
server:
  port: 8001
# 服务注册中心实例的主机名
eureka:
  instance:
    hostname: 127.0.0.1
    
  server:
    # 详解：https://www.cnblogs.com/breath-taking/articles/7940364.html
    # 定义 renews 和 renews threshold 的比值
    renewalPercentThreshold: 0.49
    
  client:
    # 是否向服务注册中心注册自己（默认配置下为 true）
    register-with-eureka: false
    # 是否检索服务
    fetch-registry: false
    serviceUrl:
      # 服务注册中心的配置内容，指定服务注册中心的位置
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
```

##### Application.java 配置

```java
@EnableEurekaServer
@SpringBootApplication
public class CloudEurekaApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudEurekaApplication.class, args);
    }
}
```



### Eureka Client 实现

目的：

1、配置`server-a`、`server-b`为服务提供者


#### 相关配置
##### pom.xml 配置
说明：由于主工程有引入`eureka-server`组件，组件中包含了`eureka-client`组件，所以不需要配置

##### application.yml 配置
```yml
server:
  port: 8101

eureka:
  port: 8001
  instance:
    hostname: 127.0.0.1
  client:
    serviceUrl:
      defaultZone: http://${eureka.instance.hostname}:${eureka.port}/eureka/
```

##### Application.java 配置

```java
@EnableEurekaClient
@SpringBootApplication
public class ModuleAApplication {
    public static void main(String[] args) {
        SpringApplication.run(ModuleAApplication.class, args);
    }
}
```



### 测试运行

**测试链接：**http://127.0.0.1:8001/








**参考文章**

Spring Boot相关：

[springboot(二)Actuator的作用和使用](https://blog.csdn.net/tangyaya8/article/details/81157747)

Spring Cloud相关：

[面试不再慌！跟着老司机吃透Spring Cloud](https://mp.weixin.qq.com/s?__biz=MjM5ODI5Njc2MA==&mid=2655826147&idx=1&sn=ece0b1912830846414fdf21d96b11a44&chksm=bd74fd348a03742211da706ba8f396cf71fc82bf49d21b9173420c877d0bd9e9333a3bceb152&mpshare=1&scene=1&srcid=0108VvM18eeezjCYC2vxI3d9&sharer_sharetime=1578481530456&sharer_shareid=c6be6ccf1fcacb1f2cb811609a2c9f71&key=17fbc717c1803f30fc32c5827d506544efaf51172115d6756cbf0ef8bbd1d6c50e5b3a7b1f579aef165c251883223599101d7d8016cc504adecd8b6e7e9bf3f50b7f2e1ded3e3da964c8fab9ea57f094&ascene=1&uin=MTI2NTAxNDkzMw%3D%3D&devicetype=Windows+10&version=6208006f&lang=zh_CN&exportkey=A6CkxV9dn%2B8zN3a3rH0SE6g%3D&pass_ticket=1XkMOeeAYS1u4dEzKlBGNYHXRvT6uXdFz2is0OZpUNXpi0BIsJT766ZKNl6%2BfYwd)

[SpringCloud多模块项目搭建](https://blog.csdn.net/qq_36688143/article/details/82755492)

[springcloud @EnableDiscoveryClient注解作用](https://blog.csdn.net/zheng199172/article/details/82466139)

[Spring Cloud 常用框架组件](https://blog.csdn.net/qiuyinthree/article/details/80408751)