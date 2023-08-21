package tmen.utilspringbootstart.logutil.exceptionadapter;

import org.slf4j.Logger;
import org.springframework.boot.logging.LogLevel;
import tmen.utilspringbootstart.logutil.LogManager;
import tmen.utilspringbootstart.logutil.annotation.ExceptionRegister;
import tmen.utilspringbootstart.logutil.constant.ConstantStr;

@ExceptionRegister(name = {ConstantStr.DEFAULT_HANDLER})
public class DefaultExceptionHandler implements ExceptionHandler {

    @Override
    public LogLevel isCriticalException() {
        return LogLevel.WARN;
    }

    @Override
    public void handleException(Exception e) {
        String simpleName = getClass().getSimpleName();
        LogManager.logLevel(simpleName, isCriticalException(), e);
    }

    @Override
    public void handleException(String title, Exception e) {
        LogManager.logLevel(title, isCriticalException(), e);
    }

    @Override
    public void handleException(String title, String msg, Exception e) {
        LogManager.logLevel(title, isCriticalException(), msg, e);
    }

}
