package com.tmen.exception.util.annotation;


import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface ExceptionRegister {

    /**
     * handler name
     */
    String[] name() default {};

    /**
     * handler log level
     */
    LogLevel logLevel() default LogLevel.ERROR;


    /**
     * handler order
     */
    int order() default 9;
}
