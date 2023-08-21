package tmen.utilspringbootstart.logutil.exceptionadapter;


import tmen.utilspringbootstart.logutil.LogManager;
import tmen.utilspringbootstart.logutil.annotation.ExceptionRegister;

@ExceptionRegister(name = {"NullPointerException"})
public class NullPointerExceptionHandler implements ExceptionHandler {

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
