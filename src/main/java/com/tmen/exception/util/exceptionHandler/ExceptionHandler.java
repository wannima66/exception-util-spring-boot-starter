package com.tmen.exception.util.exceptionHandler;


import com.tmen.exception.util.bean.HandleExceptionLog;
import org.springframework.boot.logging.LogLevel;
import com.tmen.exception.util.annotation.ExceptionRegister;

public interface ExceptionHandler {

    default LogLevel level() {
        return this.getClass().getAnnotation(ExceptionRegister.class).logLevel();
    }

    void handleException(Throwable e);

    void handleException(String title, Throwable e);

    void handleException(String title, String msg);

    void handleException(String title, String msg, Throwable e);

    void handleException(HandleExceptionLog record);

}
