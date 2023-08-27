package com.tmen.exception.util.aop;

import com.tmen.exception.util.annotation.HandleException;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Objects;

public class LogRecordPointcut  extends StaticMethodMatcherPointcut implements Serializable {
    @Override
    public boolean matches(Method method, Class<?> aClass) {
        HandleException annotation = method.getAnnotation(HandleException.class);
        return Objects.nonNull(annotation);
    }
}
