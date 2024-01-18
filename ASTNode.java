public class ASTNode {
}

class NumberNode extends ASTNode {
    double value;

    public NumberNode(double value) {
        assert !Double.isNaN(value) : "Value should not be NaN";
        this.value = value;
    }
}

class VariableNode extends ASTNode {
    String name;
    public VariableNode(String name) {
        assert name != null : "Name should not be null";
        this.name = name;
    }
}
