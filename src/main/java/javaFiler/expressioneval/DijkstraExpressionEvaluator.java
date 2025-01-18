package javaFiler.expressioneval;

import javaFiler.models.ExpressionEvaluator;

import java.text.DecimalFormat;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DijkstraExpressionEvaluator implements ExpressionEvaluator {
    public double eval(final String expression){
        Stack<Double> values = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char token = expression.charAt(i);

            // If the token is a whitespace, skip it
            if (token == ' ') {
                continue;
            }

            // If the token is a number, push it to the values stack
            if (Character.isDigit(token)) {
                StringBuilder sb = new StringBuilder();
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    sb.append(expression.charAt(i++));
                }
                values.push(Double.parseDouble(sb.toString()));
                i--; // Adjust i because of the extra increment in the while loop
            }
            // If the token is '(', push it to the operators stack
            else if (token == '(') {
                operators.push(token);
            }
            // If the token is ')', solve the entire brace
            else if (token == ')') {
                while (operators.peek() != '(') {
                    values.push(applyOperation(operators.pop(), values.pop(), values.pop()));
                }
                operators.pop(); // Remove the '(' from the stack
            }
            // If the token is an operator
            else if (token == '+' || token == '-' || token == '*' || token == '/') {
                while (!operators.isEmpty() && hasPrecedence(token, operators.peek())) {
                    values.push(applyOperation(operators.pop(), values.pop(), values.pop()));
                }
                operators.push(token);
            }
        }

        // Apply remaining operators
        while (!operators.isEmpty()) {
            values.push(applyOperation(operators.pop(), values.pop(), values.pop()));
        }

        return values.pop();
    }

    // Method to determine operator precedence
    private static boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')') {
            return false;
        }
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) {
            return false;
        }
        return true;
    }

    // Method to perform the arithmetic operations
    private static double applyOperation(char op, double b, double a) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) throw new UnsupportedOperationException("Cannot divide by zero");
                return a / b;
        }
        return 0;
    }
    @Override
    public String processExpressions(final String content){
        String regex = "\\b(\\d+(\\.\\d+)?([+\\-*/]\\d+(\\.\\d+)?)*)\\b|\\(([^()]*|\\(([^()]*|\\([^()]*\\))*\\))*\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);

        StringBuilder result = new StringBuilder();

        while (matcher.find()) {
            try {
                String expression = matcher.group();
                Object evaluatedResult = eval(expression);
                DecimalFormat df = new DecimalFormat((Double)evaluatedResult % 1 == 0? "0" : "0.00");
                matcher.appendReplacement(result, df.format(evaluatedResult));
            } catch (Exception e) {
                matcher.appendReplacement(result, matcher.group());
            }
        }
        matcher.appendTail(result);

        return result.toString();
    }
}
