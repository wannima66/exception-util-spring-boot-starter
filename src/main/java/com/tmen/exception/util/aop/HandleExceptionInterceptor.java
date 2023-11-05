package com.tmen.exception.util.aop;

import com.tmen.exception.util.bean.HandleExceptionLog;
import com.tmen.exception.util.bean.HandleExceptionOperation;
import com.tmen.exception.util.bean.MethodExecuteResult;
import com.tmen.exception.util.context.HandleExceptionContext;
import com.tmen.exception.util.log.ILogService;
import com.tmen.exception.util.parse.SpElValueParser;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class HandleExceptionInterceptor implements MethodInterceptor, Serializable, SmartInitializingSingleton, BeanFactoryAware {
    public static final String COMMA = ",";
    public static final String EQUAL = "=";
    private HandleExceptionOperationSource handleExceptionOperationSource;

    private SpElValueParser spElValueParser;

    protected BeanFactory beanFactory;

    private ILogService iLogService;
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Method method = methodInvocation.getMethod();
        return execute(methodInvocation, methodInvocation.getThis(), method, methodInvocation.getArguments());
    }

    private Object execute(MethodInvocation invoker, Object target, Method method, Object[] args) throws Throwable {
        Object ret = null;
        HandleExceptionContext.putEmptySpan();
        Collection<HandleExceptionOperation> operations = new ArrayList<>();
        Class<?> targetClass = getTargetClass(target);
        MethodExecuteResult methodExecuteResult = new MethodExecuteResult(method, args, targetClass);
        try {
            operations = handleExceptionOperationSource.computeHandleExceptionOperations(method, targetClass);
        } catch (Exception e) {
            log.error("parse handle exception operation exception", e);
        }
        try{
            ret = invoker.proceed();
            methodExecuteResult.setSuccess(true);
            methodExecuteResult.setResult(ret);
        } catch (Exception e) {
            methodExecuteResult.setSuccess(false);
            methodExecuteResult.setThrowable(e);
            methodExecuteResult.setErrorMsg(e.getMessage());
        }

        try {
            if (!CollectionUtils.isEmpty(operations) && !methodExecuteResult.isSuccess()) {
                recordExecute(methodExecuteResult, operations);
            }
        } catch (Exception e) {
            log.error("log record parse exception", e);
        } finally {
            HandleExceptionContext.clear();
        }
        return ret;
    }



    public void setSpElValueParser(SpElValueParser parser) {
        this.spElValueParser = parser;
    }

    private Class<?> getTargetClass(Object target) {
        return AopProxyUtils.ultimateTargetClass(target);
    }

    private void recordExecute(MethodExecuteResult methodExecuteResult, Collection<HandleExceptionOperation> operations) {
        for (HandleExceptionOperation operation : operations) {
            try {
                if (StringUtils.isBlank(operation.getLogTemplate())) {
                    continue;
                }
                executeOperation(methodExecuteResult, operation);
            } catch (Exception e) {
                log.error("handle exception fail", e);
            }
        }

    }

    private void executeOperation(MethodExecuteResult methodExecuteResult, HandleExceptionOperation operation) {
        String action = operation.getLogTemplate();
        if (StringUtils.isBlank(action)) {
            return;
        }
        List<String> spElTemplates = getSpElTemplates(operation, action);
        Map<String, String> expressionValues = spElValueParser.processTemplate(spElTemplates, methodExecuteResult);
        recordLog(methodExecuteResult, operation, action, expressionValues);
    }

    /**
     * 记录日志
     */
    private void recordLog(MethodExecuteResult methodExecuteResult, HandleExceptionOperation operation, String action,
                                 Map<String, String> expressionValues) {

        HandleExceptionLog handleExceptionLog = HandleExceptionLog.builder()
                .handlerName(operation.getHandlerName())
                .title(parseLogTitle(methodExecuteResult, operation, expressionValues))
                .message(expressionValues.get(action))
                .createTime(LocalDateTime.now())
                .exception(methodExecuteResult.getThrowable())
                .traceStack(operation.isTraceStack())
                .args(methodExecuteResult.getArgs())
                .method(methodExecuteResult.getMethod())
                .build();
        iLogService.record(handleExceptionLog);
    }

    private String parseLogTitle(MethodExecuteResult methodExecuteResult, HandleExceptionOperation operation, Map<String, String> expressionValues) {
        String title = expressionValues.getOrDefault(operation.getTitle(), operation.getTitle());
        return StringUtils.isBlank(title) ? methodExecuteResult.getTargetClass().getSimpleName() : title;
    }


    private List<String> getSpElTemplates(HandleExceptionOperation operation, String... actions) {
        List<String> spElTemplates = new ArrayList<>();
        spElTemplates.add(operation.getTitle());
        spElTemplates.addAll(Arrays.asList(actions));
        return spElTemplates;
    }


    private Map<String, String> parseLogTags(String tags) {
        if (StringUtils.isBlank(tags)) {
            return null;
        }
        return Arrays.stream(tags.split(COMMA)).map(pair -> pair.split(EQUAL))
                .filter(parts -> parts.length == 2)
                .collect(Collectors.toMap(
                        parts -> parts[0].trim(),
                        parts -> parts[1].trim()
                ));
    }

    public void setExceptionHandleOperationSource(HandleExceptionOperationSource handleExceptionOperationSource) {
        this.handleExceptionOperationSource = handleExceptionOperationSource;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void afterSingletonsInstantiated() {
        iLogService = beanFactory.getBean(ILogService.class);
        spElValueParser = beanFactory.getBean(SpElValueParser.class);
    }
}
