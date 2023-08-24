package com.tmen.exception.util.exceptionHandler;

import com.tmen.exception.util.constant.Constant;
import com.tmen.exception.util.annotation.ExceptionRegister;


import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ExceptionHandlerRegister {

    private final static Map<String, ExceptionHandler> adapterCache = new ConcurrentHashMap<>();

    public static  void put(String beanName, ExceptionHandler bean) {
        if (checkBeanOrder(beanName, bean)) {
            return;
        }
        adapterCache.put(beanName, bean);
    }

    public static ExceptionHandler get(String beanName) {
        ExceptionHandler adapter = adapterCache.get(beanName);
        return Optional.ofNullable(adapter).orElseGet(() -> adapterCache.get(Constant.DEFAULT_HANDLER));
    }

    private static boolean checkBeanOrder(String beanName, ExceptionHandler bean) {
        ExceptionHandler existHandler = adapterCache.get(beanName);
        if (existHandler == null) {
            return false;
        }
        int oldOrder = getOrder(existHandler);
        int newOrder = getOrder(bean);
        return oldOrder <= newOrder;
    }

    private static int getOrder(ExceptionHandler existHandler) {
        return existHandler.getClass().getAnnotation(ExceptionRegister.class).order();
    }
}
