package tmen.utilspringbootstart.logutil.annotation;


import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface ExceptionRegister {

    /**
     * 优先级高
     * @return
     */
    String[] name() default {};

    /**
     * 日志等级
     */
    LogLevel logLevel() default LogLevel.ERROR;
}
