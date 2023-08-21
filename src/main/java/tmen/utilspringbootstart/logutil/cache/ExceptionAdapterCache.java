package tmen.utilspringbootstart.logutil.cache;

import tmen.utilspringbootstart.logutil.constant.ConstantStr;
import tmen.utilspringbootstart.logutil.exception.DuplicateBeanException;
import tmen.utilspringbootstart.logutil.exceptionadapter.ExceptionHandler;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ExceptionAdapterCache {

    private final static Map<String, ExceptionHandler> adapterCache = new ConcurrentHashMap<>();

    public static  void put(String beanName, ExceptionHandler bean) {
        // todo 如果覆盖默认的adapter，会报错
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
        // 在这里实现检查是否存在重复 Bean 定义的逻辑
        // 返回 true 表示存在重复定义，否则返回 false
        // 这可能需要根据你的具体情况来实现
        return adapterCache.containsKey(beanName);
    }
}
