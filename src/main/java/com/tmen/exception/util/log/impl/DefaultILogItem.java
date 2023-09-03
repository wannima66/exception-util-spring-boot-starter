package com.tmen.exception.util.log.impl;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tmen.exception.util.log.ILogWrapper;

/**
 * @author guofazhang
 * @date 2023/8/22 17:22
 */
@Getter
public class DefaultILogItem implements ILogWrapper {

    private final Logger logger;

    private final String title;

    public DefaultILogItem() {
        this.title = getClass().getSimpleName();
        this.logger = LoggerFactory.getLogger(DefaultILogItem.class);
    }

    public DefaultILogItem(String title) {
        this.title = title;
        this.logger = LoggerFactory.getLogger(DefaultILogItem.class);
    }

    @Override
    public void info(String message) {
        logger.info(message);
    }

    @Override
    public void info(String message, Throwable throwable) {
        logger.info(message, throwable);
    }

    @Override
    public void warn(String message) {
        logger.warn(message);
    }


    @Override
    public void warn(String message, Throwable throwable) {
        logger.warn(message, throwable);
    }

    @Override
    public void error(String message) {
        logger.error(message);
    }

    @Override
    public void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }
}
