package tmen.utilspringbootstart.logutil.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import tmen.utilspringbootstart.logutil.annotation.ExceptionRegister;
import tmen.utilspringbootstart.logutil.exceptionHandler.ExceptionHandlerRegister;
import tmen.utilspringbootstart.logutil.exceptionHandler.ExceptionHandler;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@Configuration
public class ExceptionAdapterProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        ExceptionRegister annotation = bean.getClass().getAnnotation(ExceptionRegister.class);
        if (Objects.isNull(annotation) || !(bean instanceof ExceptionHandler)) {
            return bean;
        }
        System.out.println("==加载bean=== "+ beanName);
        ExceptionHandler adapter = (ExceptionHandler)bean;
        String[] names = annotation.name();
        List<String> nameList = names.length > 0 ? Arrays.asList(names) : List.of(adapter.getClass().getSimpleName());
        nameList.forEach(name -> ExceptionHandlerRegister.put(beanName, adapter));
        return bean;
    }
}
