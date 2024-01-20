package main;

public class Token {
    String type;

    String value;

    // Vorbedingung: type und value sollten nicht null sein
    // Konstruktor für die Initialisierung eines Tokens mit gegebenem Typ und Wert
    public Token(String type, String value) {
        assert type != null : "Type should not be null";
        assert value != null : "Value should not be null";
        this.type = type;
        this.value = value;
    }
    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
