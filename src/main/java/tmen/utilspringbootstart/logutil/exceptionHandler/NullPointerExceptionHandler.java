package tmen.utilspringbootstart.logutil.exceptionHandler;


import tmen.utilspringbootstart.logutil.annotation.ExceptionRegister;
import tmen.utilspringbootstart.logutil.log.LogManagerUtil;

@ExceptionRegister(name = {"NullPointerException"})
public class NullPointerExceptionHandler implements ExceptionHandler {

    @Override
    public void handleException(Exception e) {
        LogManagerUtil.logLevel(level(), e);
    }

    @Override
    public void handleException(String title, Exception e) {
        LogManagerUtil.logLevel(level(), title, e);

    }

    @Override
    public void handleException(String title, String msg, Exception e) {
        LogManagerUtil.logLevel(level(), title, msg, e);
    }
}
