package main;

public class NumberNode implements ASTNode {
    double value;

    // Vorbedingung: Der Wert sollte nicht NaN sein
    // Konstruktor für die Initialisierung eines NumberNode mitgegebenem Wert
    public NumberNode(double value) {
        assert !Double.isNaN(value) : "Value should not be NaN";
        this.value = value;
    }
}
