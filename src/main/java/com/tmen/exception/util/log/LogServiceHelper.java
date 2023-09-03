package com.tmen.exception.util.log;

import com.tmen.exception.util.bean.HandleExceptionLog;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.logging.LogLevel;

import java.util.Objects;

public class LogServiceHelper {

    private static volatile LogFactory currentFactory = null;

    private LogServiceHelper() {
        throw new IllegalStateException("Utility class");
    }
    public LogServiceHelper(LogFactory factory) {
        currentFactory = factory;
    }

    public void logLevel(HandleExceptionLog record) {
        if (record.isTraceStack()) {
            logLevel(record.getLogLevel(), record.getTitle(), record.getMessage(), record.getException());
        } else {
            logLevel(record.getLogLevel(), record.getTitle(), record.getMessage());
        }
    }
    public void logLevel(LogLevel level, Throwable e) {
        logLevel(level, StringUtils.EMPTY, e.getMessage());
    }

    public void logLevel(LogLevel level, String msg) {
        logLevel(level, StringUtils.EMPTY, msg);
    }

    public void logLevel(LogLevel level, String title, String msg) {
        ILogWrapper logger = getLog(title);
        if (LogLevel.WARN.equals(level)) {
            logger.warn(msg);
        } else if (LogLevel.ERROR.equals(level)) {
            logger.error(msg);
        } else {
            logger.info(msg);
        }
    }

    public void logLevel(LogLevel level, String title, String message, Throwable e) {
        ILogWrapper logger = getLog(title);
        if (LogLevel.WARN.equals(level)) {
            logger.warn(message, e);
        } else if (LogLevel.ERROR.equals(level)) {
            logger.error(message, e);
        } else {
            logger.info(message, e);
        }
    }

    private ILogWrapper getLog(String title) {
        return StringUtils.isNotBlank(title) ? currentFactory.getLogger(title) : getLog();
    }

    private ILogWrapper getLog(Class<?> clazz) {
        return Objects.nonNull(clazz) ? currentFactory.getLogger(clazz) : getLog();
    }
    private ILogWrapper getLog() {
        return currentFactory.getLogger();
    }
}
