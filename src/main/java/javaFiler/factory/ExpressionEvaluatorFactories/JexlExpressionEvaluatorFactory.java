package javaFiler.factory.ExpressionEvaluatorFactories;

import javaFiler.models.ExpressionEvaluator;
import javaFiler.service.ExpressionHandlers.DijkstraExpressionEvaluator;
import javaFiler.service.ExpressionHandlers.JexlExpressionEvaluator;

public class JexlExpressionEvaluatorFactory implements ExpressionEvaluatorFactory{
    @Override
    public ExpressionEvaluator createExpressionEvaluator(){
        return new JexlExpressionEvaluator();
    }
}
