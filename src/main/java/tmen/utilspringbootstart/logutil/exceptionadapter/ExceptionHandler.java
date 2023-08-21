package tmen.utilspringbootstart.logutil.exceptionadapter;


import org.springframework.boot.logging.LogLevel;

public interface ExceptionHandler {

    /**
     * 日志等级
     * @return true-error级别 false-warn级别
     */
    default LogLevel isCriticalException() {
        return LogLevel.ERROR;
    }


    /**
     * 捕获异常后处理
     * @param e 异常
     */
    default void handleException(Exception e) {
        System.out.println("记录日志："+ e);
    }

    void handleException(String title, Exception e);
    void handleException(String title, String msg, Exception e);


}
