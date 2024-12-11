package javaFiler.service.ExpressionHandlers;

import javaFiler.models.ExpressionEvaluator;
import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.MapContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JexlExpressionEvaluator implements ExpressionEvaluator {
    @Override
    public String processExpressions(final String str) {
        JexlEngine jexlEngine = new JexlBuilder().create();
        String regex = "\\b(\\d+(\\.\\d+)?([+\\-*/]\\d+(\\.\\d+)?)*)\\b|\\(([^()]*|\\(([^()]*|\\([^()]*\\))*\\))*\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);

        StringBuilder result = new StringBuilder();

        while (matcher.find()) {
            try {
                String expression = matcher.group();
                JexlExpression jexlExpression = jexlEngine.createExpression(expression);
                Object evaluatedResult = jexlExpression.evaluate(new MapContext());
                matcher.appendReplacement(result, evaluatedResult.toString());
            } catch (Exception e) {
                // В случае ошибки парсинга оставляем выражение как есть
                matcher.appendReplacement(result, matcher.group());
            }
        }
        matcher.appendTail(result);

        return result.toString();
    }
}
