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
     */
    private String handlerName;
    /**
     * 日志的title
     */
    private String title;

    /**
     * 日志内容
     */
    private String message;

    /**
     * tag信息
     */
    private Map<String, String> tags;

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
