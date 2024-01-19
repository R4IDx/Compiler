import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private String input;

    private int position;

    // Vorbedingung: Der Eingabestring sollte nicht null sein
    // Methode zur Lexikalischen Analyse des gegebenen Eingabestrings und Erstellung einer Liste von Tokens
    public List<Token> lex(String s) {
        assert s != null : "Input string should not be null";

        this.input = s;
        this.position = 0;
        List<Token> tokens = new ArrayList<>();

        while (position < input.length()) {
            char currentChar = input.charAt(position);

            // Invariante: Der currentChar sollte nicht null sein
            assert currentChar != 0 : "Current character should not be null";

            if (Character.isDigit(currentChar) || currentChar == '.') {
                // Wenn der aktuelle Charakter eine Ziffer oder ein Punkt ist, analysiere eine Zahl
                tokens.add(lexNumber());
            } else if (Character.isLetter(currentChar)) {
                // Wenn der aktuelle Charakter ein Buchstabe ist, analysiere eine Variable
                tokens.add(lexVariable());
            } else if (currentChar == '+' || currentChar == '-' || currentChar == '*' || currentChar == '/'
                    || currentChar == '^' || currentChar == '(' || currentChar == ')') {
                // Wenn der aktuelle Charakter ein Operator oder eine Klammer ist, füge ein entsprechendes Token hinzu
                tokens.add(new Token("SPECIAL", String.valueOf(currentChar)));
                position++;
            } else if (currentChar == ' ') {
                position++; // Ignoriere Leerzeichen
            } else {
                // Umgang mit ungültigen Zeichen
                throw new IllegalArgumentException("Invalid character at position: " + position);
            }
        }
        return tokens;
    }

    // Methode zur Analyse von Zahlen
    private Token lexNumber() {
        StringBuilder sb = new StringBuilder();
        while (position < input.length() && (Character.isDigit(input.charAt(position)) || input.charAt(position) == '.')) {
            sb.append(input.charAt(position));
            position++;
        }
        return new Token("NUMBER", sb.toString());
    }

    // Methode zur Analyse von Variablen
    private Token lexVariable() {
        StringBuilder sb = new StringBuilder();
        while (position < input.length() && Character.isLetter(input.charAt(position))) {
            sb.append(input.charAt(position));
            position++;
        }
        return new Token("VARIABLE", sb.toString());
    }
}
