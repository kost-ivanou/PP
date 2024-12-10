package javaFiler.service;

import javaFiler.factory.ExpressionEvaluatorFactories.AlgebraicExpressionEvaluatorFactory;
import javaFiler.factory.ExpressionEvaluatorFactories.DijkstraExpressionEvaluatorFactory;
import javaFiler.factory.ExpressionEvaluatorFactories.ExpressionEvaluatorFactory;
import javaFiler.factory.ExpressionEvaluatorFactories.JexlExpressionEvaluatorFactory;
import javaFiler.models.ExpressionEvaluator;
import javaFiler.service.ExpressionHandlers.AlgebraicExpressionsEvaluator;
import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.MapContext;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringProcessor {
    private ExpressionEvaluatorFactory factory;
    private ExpressionEvaluator evaluator;

    public StringProcessor() {
        Random random = new Random();
        int choice = random.nextInt(3)+1;
        try {
            switch (choice) {
                case 1:
                    factory = new AlgebraicExpressionEvaluatorFactory();
                    break;
                case 2:
                    factory = new DijkstraExpressionEvaluatorFactory();
                    break;
                case 3:
                    factory = new JexlExpressionEvaluatorFactory();
                    break;
                default:
                    factory = null;
            }
            evaluator = factory.createExpressionEvaluator();
        } catch(NullPointerException e){
            e.getStackTrace();
        }
    }

    public String evaluateExpressions(String content){
        return evaluator.processExpressions(content);
    }
}

