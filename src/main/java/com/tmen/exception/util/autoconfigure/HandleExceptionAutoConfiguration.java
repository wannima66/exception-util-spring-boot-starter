package com.tmen.exception.util.autoconfigure;

import com.tmen.exception.util.ExceptionHelper;
import com.tmen.exception.util.aop.BeanFactoryExceptionHandleAdvisor;
import com.tmen.exception.util.aop.HandleExceptionOperationSource;
import com.tmen.exception.util.aop.HandleExceptionInterceptor;
import com.tmen.exception.util.log.ILogService;
import com.tmen.exception.util.log.LogFactory;
import com.tmen.exception.util.log.LogServiceHelper;
import com.tmen.exception.util.log.impl.DefaultLogServiceImpl;
import com.tmen.exception.util.parse.SpElValueParser;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

@Configuration
@ComponentScan({"com.tmen.exception.util"})
@EnableConfigurationProperties(value = HandleExceptionProperties.class)
public class HandleExceptionAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(ExceptionHelper.class)
    public ExceptionHelper exceptionHelper ()  {
        return new ExceptionHelper();
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public BeanFactoryExceptionHandleAdvisor beanFactoryExceptionHandleAdvisor() {
        BeanFactoryExceptionHandleAdvisor advisor = new BeanFactoryExceptionHandleAdvisor();
        advisor.setAdvice(handleExceptionInterceptor());
        return advisor;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public HandleExceptionInterceptor handleExceptionInterceptor() {
        HandleExceptionInterceptor handleExceptionInterceptor = new HandleExceptionInterceptor();
        handleExceptionInterceptor.setExceptionHandleOperationSource(handleExceptionOperationSource());
        return handleExceptionInterceptor;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LogFactory logFactory(HandleExceptionProperties properties){
        return new LogFactory(properties);
    }
    @Bean
    public LogServiceHelper logServiceHelper(LogFactory logFactory) {
       return new LogServiceHelper(logFactory);
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public HandleExceptionOperationSource handleExceptionOperationSource() {
        return new HandleExceptionOperationSource();
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public SpElValueParser spElValueParser(BeanFactory beanFactory) {
        SpElValueParser spElValueParser = new SpElValueParser();
        spElValueParser.setBeanFactory(beanFactory);
        return spElValueParser;
    }

    @Bean
    public ILogService iLogService(){
        return new DefaultLogServiceImpl();
    }

}
