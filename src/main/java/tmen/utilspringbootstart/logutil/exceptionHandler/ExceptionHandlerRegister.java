package tmen.utilspringbootstart.logutil.exceptionHandler;

import tmen.utilspringbootstart.logutil.constant.ConstantStr;
import tmen.utilspringbootstart.logutil.exception.DuplicateBeanException;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ExceptionHandlerRegister {

    private final static Map<String, ExceptionHandler> adapterCache = new ConcurrentHashMap<>();


    public static void put(String beanName, ExceptionHandler bean) {
        if (isDuplicateBeanDefinition(beanName)) {
            throw new DuplicateBeanException("Duplicate exception adapter definition detected for: " + beanName);
        }
        adapterCache.put(beanName, bean);
    }

    public static ExceptionHandler get(String beanName) {
        ExceptionHandler adapter = adapterCache.get(beanName);
        return Optional.ofNullable(adapter).orElseGet(() -> adapterCache.get(ConstantStr.DEFAULT_HANDLER));
    }

    private static boolean isDuplicateBeanDefinition(String beanName) {
        return adapterCache.containsKey(beanName);
    }
}
