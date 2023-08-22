package tmen.utilspringbootstart.logutil.autoconfigure;

import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import tmen.utilspringbootstart.logutil.LogTemplate;
import tmen.utilspringbootstart.logutil.log.LogFactory;
import tmen.utilspringbootstart.logutil.log.LogManagerUtil;

@Configuration
@ComponentScan(value = {"tmen.utilspringbootstart.logutil"})
@EnableConfigurationProperties(value = LogUtilsProperties.class)
@ConditionalOnProperty(name = "log.util.enable")
public class LogUtilAutoConfiguration  {

    @Autowired
    private LogUtilsProperties logUtilsProperties;

    @Bean
    @ConditionalOnMissingBean(name = "LogTemplate")
    public LogTemplate logTemplate ()  {
        System.out.println("== 初始化 日志模板工具 ===: " + JSONUtil.toJsonStr(logUtilsProperties));
        initFactory();
        return new LogTemplate();
    }

    private void initFactory() {
        if (!StringUtils.hasLength(logUtilsProperties.getLogClass())){
            throw new IllegalArgumentException("Property 'logClass' cannot be null.");
        }
        LogManagerUtil.setFactory(new LogFactory(logUtilsProperties.getLogClass()));
    }
}
