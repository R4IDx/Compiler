package main;

public class VariableNode implements ASTNode {
    String name;

    // Vorbedingung: Der Name sollte nicht null sein
    // Konstruktor für die Initialisierung eines VariableNode mit gegebenem Namen
    public VariableNode(String name) {
        assert name != null : "Name should not be null";
        this.name = name;
    }
}
