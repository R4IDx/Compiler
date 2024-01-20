package main;

public class BinaryExpressionNode implements ASTNode {
    String operator;

    ASTNode left;

    ASTNode right;

    // Vorbedingungen:
    // - Der Operator sollte nicht null sein
    // - Die linken und rechten AST-Knoten sollten nicht null sein
    // Konstruktor für die Initialisierung eines BinaryExpressionNode mit gegebenem Operator, linkem und rechtem AST-Knoten
    public BinaryExpressionNode(String operator, ASTNode left, ASTNode right) {
        assert operator != null : "Operator should not be null";
        assert left != null : "Left node should not be null";
        assert right != null : "Right node should not be null";

        this.operator = operator;
        this.left = left;
        this.right = right;
    }
}
