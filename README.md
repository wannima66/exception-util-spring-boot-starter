## 简介

此组件是解决的问题是：统一系统中到处分布的异常处理逻辑

系统中各处都可能存在异常的产生，对异常的处理也充斥的代码的各处。但是，细心观察可以发现，大部分对异常处理的都是简单粗暴的且存在大量重复的处理逻辑。针对不同的异常类型又不同的处理逻辑，以及自定义的异常需要自定义的处理逻辑，将相同的处理逻辑统一，避免代码的冗余，并且能够针对特殊异常进行监控。

## 快速开始

### 引入依赖


```xml
<dependency>
    <groupId>com.ctrip.hotel</groupId>
    <artifactId>hotel-dc-exception-spring-boot-starter</artifactId>
    <version>${version}</version>
</dependency>
```

### 添加注解

在springboot启用类上添加` @EnableExceptionHandle`注解

```java
@EnableExceptionHandle
@SpringBootApplication
public class SearchBackendApplication {
 
    public static void main(String[] args) {
        SpringApplication.run(SearchBackendApplication.class, args);
    }
}
```

## 使用方法

组件提供了两种使用方式

### 对象使用

组件会自动注册`ExceptionHelper`对象，在需要使用的地方注入即可

```java
@Autowire
private ExceptionHelper exceptionHelper;
```

**1.普通使用**

根据异常类型查找对应的处理器，如果没有则使用默认的处理器。支持日志title和tag

logTitle: 日志标题

tagMap: 传入的tag

```java
public void process(long ruleId) {
        Map<String, String> tagMap = new HashMap<>();
        tagMap.put("ruleId", String.valueOf(ruleId));
        exceptionHelper.handleException(() -> {
            // do something
        }, "logTitle", tagMap);
    }
```

**2.指定异常处理器**

期望抛出异常后经由指定的处理器进行处理，可以传入异常处理器名称

handlerName : 处理器名称，在注册处理器时指定

```java
exceptionHelper.handleException("handlerName", () -> {
            // do something
        },"logTitle",tagMap);
```

**3.自定义单次处理逻辑**

期望对本次代码使用个性化逻辑处理。传入需要处理的方法即可，方法入参为抛出的异常。

```java
// 自定义异常处理逻辑
Function<Exception, Boolean> action = e -> {
           // do something;
            return false;
        };
 
exceptionHelper.handleExceptionCustom(() -> {},action);

```

**4.期望在异常处理后，执行自定义后处理逻辑**

使用`handleExceptionAfter`方法，在最后增加自定义处理的逻辑即可

```java
exceptionHelper.handleExceptionAfter("handlerName", () -> {
            //do something
        },"logTitle",tagMap, ()->{});

```

### 切面使用

**1.普通使用**

在需要的方法上增加注解`@HandleException`即可。

```java
@HandleException
public String process(String param) {
    // do something
    return "success";
}
```

**2.使用自定义日志模板**

logTemplate : 日志打印的模板

isTraceStack ：是否打印堆栈 (日志模板和堆栈是互斥的，该字段为false时才会打印模板内容，为true时只打印异常堆栈)

```java
@HandleException(logTemplate = "param:{{#param}},cause:{{#_errorMsg}}", isTraceStack = false)
    public String process(String param) {
        // do something
        return "success";
    }
```

**3.支持自定义的title，tag**

title: 日志标题，支持SpEL表达式

tags: 日志tag，支持SpEL表达式，写法： tag1=value1,tag2=value2

```java
@HandleException(title = "process_log_title",logTemplate = "param:{{#param}},cause:{{#_errorMsg}}",
            tags = "param={{#param}},ruleId=1", isTraceStack = false)
    public String process(String param) {
        // do something
        return "success";
    }
```

**4. SpEL表达式**

方法中没有的参数可以使用`HandleExceptionContext.putVariable(Name, Value)`方法添加变量；如果跨方法使用，可以通过`HandleExceptionContext.putGlobalVariable(Name, Value)`方法添加全局变量，全局变量优先级低，会被上下文的同名变量覆盖. (使用全局变量，需要自行清理)

_errorMsg: 是自带的异常信息

```java
@HandleException(title = "process_log_title",logTemplate = "param:{{#param}},name:{{#name}},cause:{{#_errorMsg}}",
            tags = "param={{#param}},ruleId=1", isTraceStack = false)
public String process(String param) {
        HandleExceptionContext.putVariable("name", "value");
        // do something
        return "success";
}
```

## 扩展

**1.扩展异常处理器**

实现`ExceptionHandler`接口，添加`ExceptionRegister`注解

name: 异常处理器名，可以定义多个，默认小驼峰类名

logLevel： 异常日志记录等级，默认error

order： 处理器顺序，越小优先级越高，只会执行一个处理器，默认9

```java
@ExceptionRegister(name = {"handerName"}, logLevel = LogLevel.WARN)
public class CustomExceptionHandler implements ExceptionHandler {
 
    @Override
    public void handleException(Exception e) {
        LogService.logLevel(level(), e);
    }
 
    @Override
    public void handleException(String title, String msg, Map<String, String> tags) {
        LogService.logLevel(level(), title, msg, tags);
    }
 
    @Override
    public void handleException(String title, Throwable e, Map<String, String> tags) {
        LogService.logLevel(level(), title, e, tags);
    }
}
```

**2.自定义日志记录器**

想要替换默认的日志记录器，可以自定义一个对象，实现`LogWrapper`接口

在配置文件中添加日志记录器路径

```properties
dc.exception.log.log-class=com.tmen.searchbackend.log.CustomLog