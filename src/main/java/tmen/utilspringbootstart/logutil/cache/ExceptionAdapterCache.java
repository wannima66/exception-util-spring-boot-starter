package tmen.utilspringbootstart.logutil.cache;

import tmen.utilspringbootstart.logutil.exception.DuplicateBeanException;
import tmen.utilspringbootstart.logutil.exceptionadapter.ExceptionAdapter;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ExceptionAdapterCache {

    private final static Map<String, ExceptionAdapter> adapterCache = new ConcurrentHashMap<>();

    public static  void put(String beanName, ExceptionAdapter bean) {
        // todo 如果覆盖默认的adapter，会报错
        if (isDuplicateBeanDefinition(beanName)) {
            throw new DuplicateBeanException("Duplicate exception adapter definition detected for: " + beanName);
        }
        adapterCache.put(beanName, bean);
    }

    public static  ExceptionAdapter get(String beanName) {
        ExceptionAdapter adapter = adapterCache.get(beanName);
        return Optional.ofNullable(adapter).orElseGet(() -> adapterCache.get("defaultExceptionAdapter"));
    }

    private static boolean isDuplicateBeanDefinition(String beanName) {
        // 在这里实现检查是否存在重复 Bean 定义的逻辑
        // 返回 true 表示存在重复定义，否则返回 false
        // 这可能需要根据你的具体情况来实现
        return adapterCache.containsKey(beanName);
    }
}
