package com.tmen.exception.util.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.SmartInitializingSingleton;

import java.io.Serializable;
import java.lang.reflect.Method;

public class LogRecordInterceptor implements MethodInterceptor, Serializable, SmartInitializingSingleton {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Method method = methodInvocation.getMethod();
        return execute(methodInvocation, methodInvocation.getThis(), method, methodInvocation.getArguments());
    }

    private Object execute(MethodInvocation invoker, Object target, Method method, Object[] args) throws Throwable {
        System.out.println("interceptor process.");
        return invoker.proceed();
    }
    @Override
    public void afterSingletonsInstantiated() {

    }
}
