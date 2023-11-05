package com.tmen.exception.util.bean;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.logging.LogLevel;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.logging.Level;

@Setter
@Builder
@Getter
@ToString
public class HandleExceptionLog {

    /**
     * 处理器名字
     * 默认匹配对应异常类型对应的处理器
     */
    private String handlerName;
    /**
     * 日志记录器的title，默认类名
     */
    private String title;

    /**
     * 日志内容
     */
    private String message;

    /**
     * 异常信息
     */
    private Throwable exception;

    /**
     * 是否打印异常堆栈
     */
    private boolean traceStack;

    /**
     * 异常的方法
     */
    private final Method method;

    /**
     * 方法参数
     */
    private final Object[] args;

    /**
     * 日志等级
     */
    private LogLevel logLevel;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
