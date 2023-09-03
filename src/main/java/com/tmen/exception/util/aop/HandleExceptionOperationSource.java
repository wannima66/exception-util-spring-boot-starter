package com.tmen.exception.util.aop;

import com.tmen.exception.util.annotation.HandleException;
import com.tmen.exception.util.bean.HandleExceptionOperation;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ClassUtils;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class HandleExceptionOperationSource {

    public Collection<HandleExceptionOperation> computeHandleExceptionOperations(Method method, Class<?> targetClass) {

        if (!Modifier.isPublic(method.getModifiers())) {
            return Collections.emptyList();
        }

        Method specificMethod = ClassUtils.getMostSpecificMethod(method, targetClass);
        specificMethod = BridgeMethodResolver.findBridgedMethod(specificMethod);


        Collection<HandleExceptionOperation> logRecordOps = parseHandleExceptionAnnotations(specificMethod);
        HashSet<HandleExceptionOperation> result = new HashSet<>();
        result.addAll(logRecordOps);
        return result;
    }

    private Collection<HandleExceptionOperation> parseHandleExceptionAnnotations(AnnotatedElement md) {
        Collection<HandleException> exceptionAnnotationAnnotations = AnnotatedElementUtils.findAllMergedAnnotations(md, HandleException.class);
        Collection<HandleExceptionOperation> ret = new ArrayList<>();
        if (!exceptionAnnotationAnnotations.isEmpty()) {
            for (HandleException recordAnnotation : exceptionAnnotationAnnotations) {
                ret.add(parseHandleExceptionAnnotation(md, recordAnnotation));
            }
        }
        return ret;
    }

    private HandleExceptionOperation parseHandleExceptionAnnotation(AnnotatedElement md, HandleException recordAnnotation) {
        return HandleExceptionOperation.builder()
                .traceStack(recordAnnotation.isTraceStack())
                .handlerName(recordAnnotation.handlerName())
                .title(recordAnnotation.title())
                .logTemplate(recordAnnotation.logTemplate())
                .tags(recordAnnotation.tags())
                .build();
    }


}
