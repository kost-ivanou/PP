package javaFiler.service;

import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.MapContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringProcessor {
    private JexlEngine jexlEngine;

    public StringProcessor() {
        jexlEngine = new JexlBuilder().create();
    }

    public String processExpressions(String content) {
        String regex = "\\([^()]*\\)|\\d+[+\\-*/]\\d+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);

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

