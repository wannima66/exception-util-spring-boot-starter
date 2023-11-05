package com.tmen.exception.util.annotation;


import org.springframework.boot.logging.LogLevel;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface HandleException {

    String handlerName() default "";
    String title() default "";

    String logTemplate();

    String tags() default "";

    boolean isTraceStack() default true;
}
