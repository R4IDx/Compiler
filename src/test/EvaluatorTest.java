package test;

import main.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EvaluatorTest {
    @Test
    void testEvaluateNumberNode() {
        Evaluator evaluator = new Evaluator();
        ASTNode numberNode = new NumberNode(5.0);
        double result = evaluator.evaluate(numberNode, 0);
        assertEquals(5.0, result);
    }

    @Test
    void testEvaluateVariableNode() {
        Evaluator evaluator = new Evaluator();
        ASTNode variableNode = new VariableNode("x");
        double result = evaluator.evaluate(variableNode, 10);
        assertEquals(10, result);
    }

    @Test
    void testEvaluateAddition() {
        Evaluator evaluator = new Evaluator();
        ASTNode additionNode = new BinaryExpressionNode("+", new NumberNode(3.0), new NumberNode(4.0));
        double result = evaluator.evaluate(additionNode, 0);
        assertEquals(7.0, result);
    }

    @Test
    void testEvaluateSubtraction() {
        Evaluator evaluator = new Evaluator();
        ASTNode subtractionNode = new BinaryExpressionNode("-", new NumberNode(8.0), new NumberNode(2.0));
        double result = evaluator.evaluate(subtractionNode, 0);
        assertEquals(6.0, result);
    }
}