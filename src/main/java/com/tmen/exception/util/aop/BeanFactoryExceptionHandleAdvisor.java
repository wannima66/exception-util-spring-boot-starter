package com.tmen.exception.util.aop;

import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;

public class BeanFactoryExceptionHandleAdvisor extends AbstractBeanFactoryPointcutAdvisor {

    private final HandleExceptionPointcut pointcut = new HandleExceptionPointcut();
    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }
}
