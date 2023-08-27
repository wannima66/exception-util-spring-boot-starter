package com.tmen.exception.util.autoconfigure;

import com.tmen.exception.util.LogTemplate;
import com.tmen.exception.util.aop.BeanFactoryLogRecordAdvisor;
import com.tmen.exception.util.aop.LogRecordInterceptor;
import com.tmen.exception.util.log.LogFactory;
import com.tmen.exception.util.log.LogManagerUtil;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.util.StringUtils;

@Configuration
@ComponentScan({"com.tmen.exception.util"})
@EnableConfigurationProperties(value = ExceptionHandleProperties.class)
public class ExceptionHandleAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(LogTemplate.class)
    public LogTemplate logTemplate (ExceptionHandleProperties exceptionHandleProperties)  {
        initFactory(exceptionHandleProperties);
        return new LogTemplate();
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public BeanFactoryLogRecordAdvisor logRecordAdvisor() {
        BeanFactoryLogRecordAdvisor advisor = new BeanFactoryLogRecordAdvisor();
        advisor.setAdvice(logRecordInterceptor());
        return advisor;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LogRecordInterceptor logRecordInterceptor() {
        return new LogRecordInterceptor();
    }

    private void initFactory(ExceptionHandleProperties exceptionHandleProperties) {
        if (!StringUtils.hasLength(exceptionHandleProperties.getLogClass())){
            throw new IllegalArgumentException("Property 'logClass' cannot be null.");
        }
        LogManagerUtil.initFactory(new LogFactory(exceptionHandleProperties.getLogClass()));
    }
}
