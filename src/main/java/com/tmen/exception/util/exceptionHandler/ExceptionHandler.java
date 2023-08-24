package com.tmen.exception.util.exceptionHandler;


import org.springframework.boot.logging.LogLevel;
import com.tmen.exception.util.annotation.ExceptionRegister;

import java.util.Map;

public interface ExceptionHandler {
    /**
     * 日志等级
     * @return true-error级别 false-warn级别
     */
    default LogLevel level() {
        return this.getClass().getAnnotation(ExceptionRegister.class).logLevel();
    }

    /**
     * 捕获异常后处理
     * @param e 异常
     */
    void handleException(Exception e);

    default void handleException(String title, Exception e) {
        handleException(title, e, null);
    }

    default void handleException(String title, String msg) {
        handleException(title, msg, null);
    }

    void handleException(String title, String msg, Map<String, String> tags);

    void handleException(String title, Throwable e, Map<String, String> tags);



}
