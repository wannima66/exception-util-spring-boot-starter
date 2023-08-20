package tmen.utilspringbootstart.logutil.exceptionadapter;



public interface ExceptionAdapter {

    /**
     * 异常类型名
     */
    String exceptionClassName();



    /**
     * 是否关键异常
     * @return true-error级别 false-warn级别
     */
    default boolean isCriticalException() {
        return true;
    }


    /**
     * 捕获异常后处理
     * @param e 异常
     */
    default void handleException(Exception e) {
        System.out.println("记录日志："+ e);
    }

}
