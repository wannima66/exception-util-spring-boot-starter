package com.tmen.exception.util.autoconfigure;

import cn.hutool.json.JSONUtil;
import com.tmen.exception.util.log.LogFactory;
import com.tmen.exception.util.log.LogManagerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import com.tmen.exception.util.LogTemplate;

@Configuration
@ComponentScan(value = {"com.tmen.exception.util"})
@EnableConfigurationProperties(value = LogUtilsProperties.class)
@ConditionalOnProperty(name = "log.util.enable", matchIfMissing = true)
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
        LogManagerUtil.initFactory(new LogFactory(logUtilsProperties.getLogClass()));
    }
}