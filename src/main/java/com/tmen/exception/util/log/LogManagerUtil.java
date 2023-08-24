package com.tmen.exception.util.log;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.logging.LogLevel;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LogManagerUtil {

    private static volatile  LogFactory currentFactory = null;

    private static final Object lock = new Object();

    public static void initFactory(LogFactory factory) {
        if (currentFactory == null) {
            synchronized (lock) {
                if (currentFactory == null) {
                    currentFactory = factory;
                }
            }
        }
    }
    public static void logLevel(LogLevel level, Throwable e) {
        logLevel(level, StringUtils.EMPTY, e);
    }

    public static void logLevel(LogLevel level, String msg) {
        logLevel(level, StringUtils.EMPTY, msg);
    }


    public static void logLevel(LogLevel level, String title, Throwable e) {
        logLevel(level, title, e, null);
    }

    public static void logLevel(LogLevel level, String title, String msg) {
        logLevel(level, title, msg, null);
    }

    public static void logLevel(LogLevel level, String title, String msg, Map<String,String> tags) {
        LogWrapper logger = getLog(title);
        tags = Objects.isNull(tags) ?  new HashMap<>(0) : tags;
        if (LogLevel.WARN.equals(level)) {
            logger.warn(msg, tags);
        } else if (LogLevel.ERROR.equals(level)) {
            logger.error(msg, tags);
        } else {
            logger.info(msg, tags);
        }
    }

    public static void logLevel(LogLevel level, String title, Throwable e, Map<String,String> tags) {
        LogWrapper logger = getLog(title);
        tags = Objects.isNull(tags) ?  new HashMap<>(0) : tags;
        if (LogLevel.WARN.equals(level)) {
            logger.warn(e, tags);
        } else if (LogLevel.ERROR.equals(level)) {
            logger.error(e, tags);
        } else {
            logger.info(e, tags);
        }
    }

    private static LogWrapper getLog(String title) {
        return StringUtils.isNotBlank(title) ? currentFactory.getLogger(title) : getLog();
    }

    private static LogWrapper getLog() {
        return currentFactory.getLogger();
    }
}
