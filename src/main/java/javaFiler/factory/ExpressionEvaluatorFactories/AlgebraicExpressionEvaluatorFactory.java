package javaFiler.factory.ExpressionEvaluatorFactories;

import javaFiler.models.ExpressionEvaluator;
import javaFiler.service.ExpressionHandlers.AlgebraicExpressionsEvaluator;

public class AlgebraicExpressionEvaluatorFactory implements ExpressionEvaluatorFactory {
    @Override
    public ExpressionEvaluator createExpressionEvaluator(){
        return new AlgebraicExpressionsEvaluator();
    }
}
