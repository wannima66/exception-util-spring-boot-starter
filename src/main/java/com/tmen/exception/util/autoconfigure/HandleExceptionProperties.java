package com.tmen.exception.util.autoconfigure;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "log.util")
public class HandleExceptionProperties {


    /**
     * 自定义日志类路径
     */
    private String logClass = "com.tmen.exception.util.log.impl.DefaultILogItem";

    public String getLogClass() {
        return logClass;
    }

    public void setLogClass(String logClass) {
        this.logClass = logClass;
    }
}
