package com.tmen.exception.util.log;

import com.tmen.exception.util.autoconfigure.HandleExceptionProperties;
import com.tmen.exception.util.context.HandleExceptionContext;
import com.tmen.exception.util.exception.InterfaceNotImplementedException;
import com.tmen.exception.util.exception.LogClassNotFoundException;
import com.tmen.exception.util.exception.ObjectCreateException;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LogFactory {

    private final HandleExceptionProperties properties;
    private final Class<?> logClass;

    private  final Map<String, ILogWrapper> logCache = new ConcurrentHashMap<>();


    public LogFactory(HandleExceptionProperties properties) {
        try {
            this.properties = properties;
            this.logClass = Class.forName(properties.getLogClass());
            if (!ILogWrapper.class.isAssignableFrom(this.logClass)) {
                throw new InterfaceNotImplementedException(this.logClass, ILogWrapper.class);
            }
        } catch (Exception e) {
            throw new LogClassNotFoundException(properties.getLogClass() + " not found: " + e.getMessage());
        }
    }
    public ILogWrapper getLogger() {
        String callerClassName = HandleExceptionContext.getCallerClassName();
        return getLogger(callerClassName);
    }

    public ILogWrapper getLogger(Class<?> clazz) {
        return logCache.computeIfAbsent(clazz.getSimpleName(), this::createLog);
    }
    public ILogWrapper getLogger(String title) {
        String callerClassName = HandleExceptionContext.getCallerClassName();
        title = StringUtils.isNotBlank(title) ? title : callerClassName;
        return logCache.computeIfAbsent(title, this::createLog);
    }

    private ILogWrapper createLog() {
        try {
            return (ILogWrapper) logClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new ObjectCreateException("Error creating object: " + e.getMessage());
        }
    }

    private ILogWrapper createLog(String title) {
        try {
            return (ILogWrapper) logClass.getDeclaredConstructor(String.class).newInstance(title);
        } catch (Exception e) {
            throw new ObjectCreateException("Error creating object: " + e.getMessage());
        }
    }

}
