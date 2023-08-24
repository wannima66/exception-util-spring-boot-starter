package com.tmen.exception.util.exceptionHandler;

import com.tmen.exception.util.annotation.ExceptionRegister;
import com.tmen.exception.util.constant.Constant;
import com.tmen.exception.util.log.LogManagerUtil;
import org.springframework.boot.logging.LogLevel;

import java.util.Map;

@ExceptionRegister(name = {Constant.DEFAULT_HANDLER}, logLevel = LogLevel.WARN)
public class DefaultExceptionHandler implements ExceptionHandler {

    @Override
    public void handleException(Exception e) {
        LogManagerUtil.logLevel(level(), e);
    }

    @Override
    public void handleException(String title, String msg, Map<String, String> tags) {
        LogManagerUtil.logLevel(level(), title, msg, tags);
    }

    @Override
    public void handleException(String title, Throwable e, Map<String, String> tags) {
        LogManagerUtil.logLevel(level(), title, e, tags);
    }
}
