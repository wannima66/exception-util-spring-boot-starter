package tmen.utilspringbootstart.logutil.exceptionadapter;

import tmen.utilspringbootstart.logutil.annotation.ExceptionRegister;

@ExceptionRegister
public class DefaultExceptionAdapter implements ExceptionAdapter {
    @Override
    public String exceptionClassName() {
        return "defaultExceptionAdapter";
    }


    @Override
    public boolean isCriticalException() {
        return false;
    }

    @Override
    public void handleException(Exception e) {
        System.out.println("默认异常处理器:" + e);
    }

}
