package javaFiler.expressioneval;

import javaFiler.models.ExpressionEvaluator;

import java.util.Random;

public class ExpressionEvaluatorFactory {
    private ExpressionEvaluator evaluator;
    public ExpressionEvaluator createExpressionEvaluator(){
        Random random = new Random();
        int choice = random.nextInt(3)+1;
            switch (choice) {
                case 1:
                    evaluator = new AlgebraicExpressionsEvaluator();
                    break;
                case 2:
                    evaluator = new DijkstraExpressionEvaluator();
                    break;
                case 3:
                    evaluator = new JexlExpressionEvaluator();
                    break;
                default:
                    evaluator = null;
            }
        return evaluator;
    }
}
