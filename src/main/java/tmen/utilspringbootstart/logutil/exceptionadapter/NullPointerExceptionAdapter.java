package tmen.utilspringbootstart.logutil.exceptionadapter;


import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import tmen.utilspringbootstart.logutil.annotation.ExceptionRegister;

@ExceptionRegister
public class NullPointerExceptionAdapter implements ExceptionAdapter {
    @Override
    public String exceptionClassName() {
        return "NullPointerException";
    }


    @Override
    public void handleException(Exception e) {
        // todo 核心 log
        System.out.println("记录下异常:" + e);
    }
}
