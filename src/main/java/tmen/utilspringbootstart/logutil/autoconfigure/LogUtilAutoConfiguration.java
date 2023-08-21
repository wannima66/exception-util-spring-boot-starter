package tmen.utilspringbootstart.logutil.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import tmen.utilspringbootstart.logutil.LogTemplate;

@Configuration
@ComponentScan(value = {"tmen.utilspringbootstart.logutil"})
@EnableConfigurationProperties(value = LogUtilsProperties.class)
@ConditionalOnProperty(name = "log.util.enable", havingValue = "true")
public class LogUtilAutoConfiguration  {

    @Autowired
    private LogUtilsProperties logUtilsProperties;

    @Bean
    @ConditionalOnMissingBean(name = "LogTemplate")
    public LogTemplate logTemplate () {
        System.out.println("== 初始化 日志模板工具 ===");
        return new LogTemplate();
    }
}
