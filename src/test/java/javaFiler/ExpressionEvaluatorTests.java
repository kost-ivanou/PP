package javaFiler;

import javaFiler.expressioneval.AlgebraicExpressionsEvaluator;
import javaFiler.expressioneval.DijkstraExpressionEvaluator;
import javaFiler.expressioneval.JexlExpressionEvaluator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExpressionEvaluatorTests {
    private final AlgebraicExpressionsEvaluator AlgEvaluator = new AlgebraicExpressionsEvaluator();
    private final DijkstraExpressionEvaluator DijEvaluator = new DijkstraExpressionEvaluator();
    private final JexlExpressionEvaluator JexEvaluator = new JexlExpressionEvaluator();
    @Test
    public void testSimpleAddition() {
        String expression = "3+5";
        String expected = "8";
        assertEquals(expected, AlgEvaluator.processExpressions(expression));
        assertEquals(expected, DijEvaluator.processExpressions(expression));
        assertEquals(expected, JexEvaluator.processExpressions(expression));
    }

    @Test
    public void testSimpleSubtraction() {
        String expression = "10-4";
        String expected = "6";
        assertEquals(expected, AlgEvaluator.processExpressions(expression));
        assertEquals(expected, DijEvaluator.processExpressions(expression));
        assertEquals(expected, JexEvaluator.processExpressions(expression));
    }

    @Test
    public void testSimpleMultiplication() {
        String expression = "6*7";
        String expected = "42";
        assertEquals(expected, AlgEvaluator.processExpressions(expression));
        assertEquals(expected, DijEvaluator.processExpressions(expression));
        assertEquals(expected, JexEvaluator.processExpressions(expression));
    }

    @Test
    public void testSimpleDivision() {
        String expression = "20/4";
        String expected = "5";
        assertEquals(expected, AlgEvaluator.processExpressions(expression));
        assertEquals(expected, DijEvaluator.processExpressions(expression));
        assertEquals(expected, JexEvaluator.processExpressions(expression));
    }

    @Test
    public void testMixedOperations() {
        String expression = "3+5*2-4/2";
        String expected = "11";
        assertEquals(expected, AlgEvaluator.processExpressions(expression));
        assertEquals(expected, DijEvaluator.processExpressions(expression));
        assertEquals(expected, JexEvaluator.processExpressions(expression));
    }

    @Test
    public void testParentheses() {
        String expression = "((2+3)*(4-1))";
        String expected = "15";
        assertEquals(expected, AlgEvaluator.processExpressions(expression));
        assertEquals(expected, DijEvaluator.processExpressions(expression));
        assertEquals(expected, JexEvaluator.processExpressions(expression));
    }

    @Test
    public void testNestedParentheses() {
        String expression = "((1+2)*(3 + 4)-5)";
        String expected = "16";
        assertEquals(expected, AlgEvaluator.processExpressions(expression));
        assertEquals(expected, DijEvaluator.processExpressions(expression));
        assertEquals(expected, JexEvaluator.processExpressions(expression));
    }

}
