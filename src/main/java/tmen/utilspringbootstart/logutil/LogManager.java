package tmen.utilspringbootstart.logutil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.logging.LogLevel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LogManager {

    private static final Map<String, Logger> logCache = new ConcurrentHashMap<>();

    public static Logger getLog(String name) {
        return logCache.computeIfAbsent(name, LogManager::createLog);
    }

    public static Logger createLog(String name) {
        return LoggerFactory.getLogger(name);
    }

    public static void logLevel(String name, LogLevel level, Throwable e) {
        logLevel(name, level, null, e);
    }

    public static void logLevel(String name, LogLevel level, String msg) {
        logLevel(name, level, msg, null);
    }

    public static void logLevel(String name, LogLevel level, String msg, Throwable e) {
        Logger logger = getLog(name);
        if (LogLevel.WARN.equals(level)) {
            logger.warn(name, msg, e);
        } else if (LogLevel.ERROR.equals(level)) {
            logger.error(name, msg, e);
        } else {
            logger.info(name, msg, e);
        }
    }
}
