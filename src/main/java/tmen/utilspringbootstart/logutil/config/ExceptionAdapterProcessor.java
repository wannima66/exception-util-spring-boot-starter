package tmen.utilspringbootstart.logutil.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import tmen.utilspringbootstart.logutil.annotation.ExceptionRegister;
import tmen.utilspringbootstart.logutil.cache.ExceptionAdapterCache;
import tmen.utilspringbootstart.logutil.exceptionadapter.ExceptionAdapter;

import java.util.Objects;


@Configuration
public class ExceptionAdapterProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        ExceptionRegister annotation = bean.getClass().getAnnotation(ExceptionRegister.class);
        // todo 判断实现了ExceptionAdapter接口
        if (Objects.isNull(annotation)) {
            return bean;
        }
        ExceptionAdapter adapter = (ExceptionAdapter)bean;
        beanName = StringUtils.hasLength(annotation.name()) ? annotation.name() : adapter.exceptionClassName();
        System.out.println("注册一个bean到map中，beanName:" + beanName);
        ExceptionAdapterCache.put(beanName, adapter);
        return bean;
    }
}
