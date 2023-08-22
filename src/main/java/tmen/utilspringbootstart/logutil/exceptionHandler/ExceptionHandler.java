package tmen.utilspringbootstart.logutil.exceptionHandler;


import org.springframework.boot.logging.LogLevel;
import tmen.utilspringbootstart.logutil.annotation.ExceptionRegister;

public interface ExceptionHandler {

    /**
     * 日志等级
     * @return true-error级别 false-warn级别
     */
    default LogLevel level() {
        return this.getClass().getAnnotation(ExceptionRegister.class).logLevel();
    }

    /**
     * 捕获异常后处理
     * @param e 异常
     */
    void handleException(Exception e);

    void handleException(String title, Exception e);
    void handleException(String title, String msg, Exception e);


}
