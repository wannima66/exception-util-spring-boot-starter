package com.tmen.exception.util.parse;

import com.tmen.exception.util.bean.MethodExecuteResult;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.expression.EvaluationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpElValueParser {

    private static final Pattern pattern = Pattern.compile("\\{\\s*(\\w*)\\s*\\{(.*?)}}");

    private final HandleExpressionEvaluator expressionEvaluator = new HandleExpressionEvaluator();
    protected BeanFactory beanFactory;
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public Map<String, String> processTemplate(List<String> spElTemplates, MethodExecuteResult methodExecuteResult) {
        Map<String, String> expressionValues = new HashMap<>();
        EvaluationContext evaluationContext = expressionEvaluator.createEvaluationContext(methodExecuteResult.getMethod(),
                methodExecuteResult.getArgs(), methodExecuteResult.getTargetClass(), methodExecuteResult.getResult(),
                methodExecuteResult.getThrowable(), beanFactory);

        for (String expressionTemplate : spElTemplates) {
            if (expressionTemplate.contains("{")) {
                Matcher matcher = pattern.matcher(expressionTemplate);
                StringBuffer parsedStr = new StringBuffer();
                AnnotatedElementKey annotatedElementKey = new AnnotatedElementKey(methodExecuteResult.getMethod(), methodExecuteResult.getTargetClass());
                while (matcher.find()) {
                    String expression = matcher.group(2);
                    expression = (String) expressionEvaluator.parseExpression(expression, annotatedElementKey, evaluationContext);
                    matcher.appendReplacement(parsedStr, Matcher.quoteReplacement(expression == null ? "" : expression));
                }
                matcher.appendTail(parsedStr);
                expressionValues.put(expressionTemplate,  parsedStr.toString());
            } else {
                expressionValues.put(expressionTemplate, expressionTemplate);
            }
        }
        return expressionValues;
    }
}
