package com.tmen.exception.util.log.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tmen.exception.util.log.LogWrapper;

import java.util.Map;

/**
 * @author guofazhang
 * @date 2023/8/22 17:22
 */
public class DefaultLogItem implements LogWrapper {

    private Logger logger;

    private final String title;

    public DefaultLogItem() {
        this.title = getClass().getSimpleName();
        Logger logger = LoggerFactory.getLogger(DefaultLogItem.class);
    }
    public DefaultLogItem(String title) {
        this.title = title;
        this.logger = LoggerFactory.getLogger(DefaultLogItem.class);
    }

    @Override
    public void info(String message) {
        logger.info(getTitle(), message);
    }

    @Override
    public void info(Throwable throwable) {
        logger.info(getTitle(), throwable);
    }

    @Override
    public void info(Throwable throwable, Map<String, String> tagMap) {
        logger.info(getTitle(), throwable, tagMap);
    }

    @Override
    public void info(String message, Map<String, String> tagMap) {
        logger.info(getTitle(), message, tagMap);
    }

    @Override
    public void warn(String message) {
        logger.warn(getTitle(), message);
    }

    @Override
    public void warn(Throwable throwable) {
        logger.warn(getTitle(), throwable);
    }

    @Override
    public void warn(String message, Map<String, String> tagMap) {
        logger.warn(getTitle(), message, tagMap);
    }

    @Override
    public void warn(Throwable throwable, Map<String, String> tagMap) {
        logger.warn(getTitle(), throwable, tagMap);
    }

    @Override
    public void error(String message) {
        logger.error(getTitle(), message);
    }

    @Override
    public void error(Throwable throwable) {
        logger.error(getTitle(), throwable);
    }

    @Override
    public void error(String message, Map<String, String> tagMap) {
        logger.error(getTitle(), message, tagMap);
    }

    @Override
    public void error(Throwable throwable, Map<String, String> tagMap) {
        logger.error(getTitle(), throwable, tagMap);
    }


    @Override
    public String getTitle() {
        return title;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}
