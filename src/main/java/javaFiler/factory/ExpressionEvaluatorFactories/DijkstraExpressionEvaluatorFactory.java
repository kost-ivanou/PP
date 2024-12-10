package javaFiler.factory.ExpressionEvaluatorFactories;

import javaFiler.models.ExpressionEvaluator;
import javaFiler.service.ExpressionHandlers.AlgebraicExpressionsEvaluator;
import javaFiler.service.ExpressionHandlers.DijkstraExpressionEvaluator;

public class DijkstraExpressionEvaluatorFactory implements ExpressionEvaluatorFactory {
    @Override
    public ExpressionEvaluator createExpressionEvaluator(){
        return new DijkstraExpressionEvaluator();
    }
}
