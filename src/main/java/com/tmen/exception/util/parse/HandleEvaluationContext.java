package com.tmen.exception.util.parse;

import com.tmen.exception.util.context.HandleExceptionContext;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.util.Map;

public class HandleEvaluationContext extends MethodBasedEvaluationContext {
    public HandleEvaluationContext(Object rootObject, Method method, Object[] arguments,
                                      ParameterNameDiscoverer parameterNameDiscoverer, Object ret, Throwable error) {
        super(rootObject, method, arguments, parameterNameDiscoverer);
        Map<String, Object> variables = HandleExceptionContext.getVariables();
        Map<String, Object> globalVariable = HandleExceptionContext.getGlobalVariableMap();
        if (variables != null) {
            setVariables(variables);
        }

        if (globalVariable != null && !globalVariable.isEmpty()) {
            for (Map.Entry<String, Object> entry : globalVariable.entrySet()) {
                if (lookupVariable(entry.getKey()) == null) {
                    setVariable(entry.getKey(), entry.getValue());
                }
            }
        }

        setVariable("_ret", ret);
        setVariable("_errorMsg", error.getMessage());
    }
}
