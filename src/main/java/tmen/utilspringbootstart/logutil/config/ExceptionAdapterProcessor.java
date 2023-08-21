package tmen.utilspringbootstart.logutil.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import tmen.utilspringbootstart.logutil.annotation.ExceptionRegister;
import tmen.utilspringbootstart.logutil.cache.ExceptionAdapterCache;
import tmen.utilspringbootstart.logutil.exceptionadapter.ExceptionHandler;

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
        ExceptionHandler adapter = (ExceptionHandler)bean;
        String[] names = annotation.name();
        List<String> nameList = names.length > 0 ? Arrays.asList(names) : List.of(adapter.getClass().getSimpleName());
        System.out.println("注册一个bean到map中，beanName:" + beanName);
        nameList.forEach(name -> ExceptionAdapterCache.put(beanName, adapter));
        return bean;
    }
}
