package com.tmen.exception.util.exceptionHandler;

import com.tmen.exception.util.annotation.ExceptionRegister;
import com.tmen.exception.util.bean.HandleExceptionLog;
import com.tmen.exception.util.constant.Constant;
import com.tmen.exception.util.log.LogServiceHelper;
import org.springframework.boot.logging.LogLevel;

@ExceptionRegister(name = {Constant.DEFAULT_HANDLER}, logLevel = LogLevel.ERROR)
public class DefaultExceptionHandler implements ExceptionHandler {

    private final LogServiceHelper logServiceHelper;

    public DefaultExceptionHandler(LogServiceHelper logServiceHelper) {
        this.logServiceHelper = logServiceHelper;
    }

    @Override
    public void handleException(Throwable e) {
        logServiceHelper.logLevel(level(), e);
    }

    @Override
    public void handleException(String title, Throwable e) {
        logServiceHelper.logLevel(level(), title, e.getMessage());
    }

    @Override
    public void handleException(String title, String msg) {
        logServiceHelper.logLevel(level(), title, msg);
    }

    @Override
    public void handleException(String title, String msg, Throwable e) {
        logServiceHelper.logLevel(level(), title, msg, e);
    }

    @Override
    public void handleException(HandleExceptionLog record) {
        record.setLogLevel(level());
        logServiceHelper.logLevel(record);
    }

}
