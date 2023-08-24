package com.tmen.exception.util.log;

import cn.hutool.core.lang.caller.CallerUtil;
import com.tmen.exception.util.exception.LogClassNotFoundException;
import com.tmen.exception.util.exception.ObjectCreateException;
import com.tmen.exception.util.exception.InterfaceNotImplementedException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LogFactory {

    Class<?> logClass;

    private  final Map<String, LogWrapper> logCache = new ConcurrentHashMap<>();


    public LogFactory(String classPath) {
        try{
           this.logClass = Class.forName(classPath);
           if (!LogWrapper.class.isAssignableFrom(this.logClass)) {
               throw new InterfaceNotImplementedException(this.logClass, LogWrapper.class);
           }
        } catch (Exception e) {
            throw new LogClassNotFoundException(classPath + "not found: " + e.getMessage());
        }
    }
    public LogWrapper getLogger() {
        return getLogger(CallerUtil.getCaller(8));
    }

    public LogWrapper getLogger(Class<?> clazz) {
        return logCache.computeIfAbsent(clazz.getSimpleName(), this::createLog);
    }
    public LogWrapper getLogger(String title) {
        return logCache.computeIfAbsent(title, this::createLog);
    }

    private LogWrapper createLog() {
        try {
            return (LogWrapper) logClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new ObjectCreateException("Error creating object: " + e.getMessage());
        }
    }

    private LogWrapper createLog(String title) {
        try {
            return (LogWrapper) logClass.getDeclaredConstructor(String.class).newInstance(title);
        } catch (Exception e) {
            throw new ObjectCreateException("Error creating object: " + e.getMessage());
        }
    }

}
