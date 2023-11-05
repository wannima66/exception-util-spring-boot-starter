package com.tmen.exception.util.annotation;


import com.tmen.exception.util.ExceptionHandleConfigureSelector;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ExceptionHandleConfigureSelector.class)
public @interface EnableExceptionHandle {


    boolean proxyTargetClass() default false;


    AdviceMode mode() default AdviceMode.PROXY;
}
