package main;

public class Evaluator {
    // Vorbedingung: Der AST-Knoten sollte nicht null sein
    // Methode zur Auswertung des gegebenen AST-Knotens unter Berücksichtigung der Variable x
    public double evaluate(ASTNode node, double x) {
        assert node != null : "ASTNode should not be null";

        if (node instanceof NumberNode) {
            // Wenn der AST-Knoten ein NumberNode ist, gibt einfach den Wert zurück
            return ((NumberNode) node).value;
        } else if (node instanceof VariableNode) {
            // Wenn der AST-Knoten eine VariableNode ist, ersetze die Variable x durch den gegebenen Wert
            return x;
        } else if (node instanceof BinaryExpressionNode) {
            // Wenn der AST-Knoten eine BinaryExpressionNode ist, werte die binäre Expression aus
            BinaryExpressionNode binaryNode = (BinaryExpressionNode) node;

            assert binaryNode.operator != null : "BinaryExpressionNode operator should not be null";

            if (isAdditionOrSubtraction(binaryNode.operator)) {
                // Addition oder Subtraktion
                double leftValue = evaluate(binaryNode.left, x);
                double rightValue = evaluate(binaryNode.right, x);
                if (binaryNode.operator.equals("+")) {
                    return leftValue + rightValue;
                } else {
                    return leftValue - rightValue;
                }
            } else if (isMultiplicationOrDivision(binaryNode.operator)) {
                // Multiplikation oder Division
                double leftValue = evaluate(binaryNode.left, x);
                double rightValue = evaluate(binaryNode.right, x);

                assert binaryNode.operator.equals("*") || binaryNode.operator.equals("/")
                        : "Invalid operator for multiplication or division";

                switch (binaryNode.operator) {
                    case "*":
                        return leftValue * rightValue;
                    case "/":
                        if (rightValue != 0) {
                            return leftValue / rightValue;
                        } else {
                            throw new ArithmeticException("Division by zero");
                        }
                    default:
                        throw new IllegalArgumentException("Invalid operator: " + binaryNode.operator);
                }
            } else if (binaryNode.operator.equals("^")) {
                // Potenzierung
                double leftValue = evaluate(binaryNode.left, x);
                double rightValue = evaluate(binaryNode.right, x);
                return Math.pow(leftValue, rightValue);
            } else {
                throw new IllegalArgumentException("Invalid operator: " + binaryNode.operator);
            }
        } else {
            throw new IllegalArgumentException("Invalid ASTNode type");
        }
    }

    // Invariante: Der Operator sollte nicht null sein
    // Hilfsmethode zur Überprüfung, ob der Operator eine Addition oder Subtraktion ist
    private boolean isAdditionOrSubtraction(String operator) {
        return operator.equals("+") || operator.equals("-");
    }

    // Invariante: Der Operator sollte nicht null sein
    // Hilfsmethode zur Überprüfung, ob der Operator eine Multiplikation oder Division ist
    private boolean isMultiplicationOrDivision(String operator) {
        return operator.equals("*") || operator.equals("/");
    }
}
