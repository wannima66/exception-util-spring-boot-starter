package tmen.utilspringbootstart.logutil.log;

import cn.hutool.json.JSONUtil;
import org.springframework.boot.logging.LogLevel;


import java.util.Map;

public class LogManagerUtil {

    private static LogFactory currentFactory = null;

    public static void setFactory(LogFactory factory) {
        currentFactory = factory;
    }
    public static void logLevel(LogLevel level, Throwable e) {
        LogWrapper logger = currentFactory.getLogger();
        if (LogLevel.WARN.equals(level)) {
            logger.warn(e);
        } else if (LogLevel.ERROR.equals(level)) {
            logger.error(e);
        } else {
            logger.info(e);
        }
    }

    public static void logLevel(LogLevel level, String title, Throwable e) {
        LogWrapper logger = currentFactory.getLogger(title);
        if (LogLevel.WARN.equals(level)) {
            logger.warn(e);
        } else if (LogLevel.ERROR.equals(level)) {
            logger.error(e);
        } else {
            logger.info(e);
        }
    }

    public static void logLevel(LogLevel level, String msg) {
        LogWrapper logger = getLog();
        if (LogLevel.WARN.equals(level)) {
            logger.warn(msg);
        } else if (LogLevel.ERROR.equals(level)) {
            logger.error(msg);
        } else {
            logger.info(msg);
        }
    }

    public static void logLevel(LogLevel level, String title, String msg) {
        LogWrapper logger = getLog(title);
        if (LogLevel.WARN.equals(level)) {
            logger.warn(msg);
        } else if (LogLevel.ERROR.equals(level)) {
            logger.error(msg);
        } else {
            logger.info(msg);
        }
    }

    public static void logLevel(LogLevel level, String title, String msg, Throwable e) {
        LogWrapper logger = getLog(title);
        String content = String.format("msg:[%s], cause:[%s]", msg, JSONUtil.toJsonStr(e));
        if (LogLevel.WARN.equals(level)) {
            logger.warn(content);
        } else if (LogLevel.ERROR.equals(level)) {
            logger.error(content);
        } else {
            logger.info(content);
        }
    }

    public static void logLevel(LogLevel level, String title, String msg, Throwable e, Map<String, String> tags) {
        logLevel(level, title, String.format("msg:[%s], cause:[%s]", msg, JSONUtil.toJsonStr(e)), tags);
    }
    public static void logLevel(LogLevel level, String title, String msg, Map<String,String> tags) {
        LogWrapper logger = getLog(title);
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
        if (LogLevel.WARN.equals(level)) {
            logger.warn(e, tags);
        } else if (LogLevel.ERROR.equals(level)) {
            logger.error(e, tags);
        } else {
            logger.info(e, tags);
        }
    }

    private static LogWrapper getLog(String title) {
        return currentFactory.getLogger(title);
    }

    private static LogWrapper getLog() {
        return currentFactory.getLogger();
    }
}
