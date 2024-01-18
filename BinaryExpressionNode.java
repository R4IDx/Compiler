public class BinaryExpressionNode extends ASTNode {
    String operator;

    ASTNode left;

    ASTNode right;


    public BinaryExpressionNode(String operator, ASTNode left, ASTNode right) {
        assert operator != null : "Operator should not be null";
        assert left != null : "Left node should not be null";
        assert right != null : "Right node should not be null";

        this.operator = operator;
        this.left = left;
        this.right = right;
    }
}
