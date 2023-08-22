package tmen.utilspringbootstart.logutil.log;

import java.util.Map;

/**
 * @author guofazhang
 * @date 2023/8/22 16:47
 */
public interface LogWrapper {

    void info(String message);

    void info(Throwable throwable);

    void info(Throwable throwable, Map<String, String> tagMap);

    void info(String message, Map<String, String> tagMap);

    void warn(String message);

    void warn(Throwable throwable);

    void warn(String message, Map<String, String> tagMap);
    void warn(Throwable throwable, Map<String, String> tagMap);

    void error(String message);

    void error(Throwable throwable);

    void error(String message, Map<String, String> tagMap);
    void error(Throwable throwable, Map<String, String> tagMap);

    String getTitle();
}
