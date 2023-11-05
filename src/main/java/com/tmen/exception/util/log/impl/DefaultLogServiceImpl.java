package com.tmen.exception.util.log.impl;

import com.tmen.exception.util.bean.HandleExceptionLog;
import com.tmen.exception.util.exceptionHandler.ExceptionHandler;
import com.tmen.exception.util.exceptionHandler.ExceptionHandlerRegister;
import com.tmen.exception.util.log.ILogService;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

public class DefaultLogServiceImpl implements ILogService {

    @Override
    public void record(HandleExceptionLog record) {
        Optional.ofNullable(getHandler(record.getHandlerName(), record.getException())).ifPresent(adapter -> adapter.handleException(record));
    }

    private ExceptionHandler getHandler(String name, Throwable e) {
        return StringUtils.isNotBlank(name) ? ExceptionHandlerRegister.get(name, exceptionSimpleName(e)) : getHandler(e);
    }

    private ExceptionHandler getHandler(Throwable e) {
        if (e == null) {
            return null;
        }
        String name = exceptionSimpleName(e);
        return ExceptionHandlerRegister.get(name);
    }

    private String exceptionSimpleName(Throwable e) {
        return e.getClass().getSimpleName();
    }



}
