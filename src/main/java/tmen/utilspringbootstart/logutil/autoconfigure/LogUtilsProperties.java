package tmen.utilspringbootstart.logutil.autoconfigure;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "log.util")
public class LogUtilsProperties {

    /**
     * 默认开启自动装配
     */
    private boolean enable = true;

    /**
     * 是否开启日志模式, 开启显示加载日志信息
     */
    private boolean log = false;


    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isLog() {
        return log;
    }

    public void setLog(boolean log) {
        this.log = log;
    }
}
