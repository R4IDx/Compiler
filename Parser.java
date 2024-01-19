import java.util.List;

public class Parser {
    private List<Token> tokens;

    private int currentTokenIndex;

    // Vorbedingung: Die Liste der Tokens sollte nicht null sein
    // Methode zur Analyse der gegebenen Liste von Tokens und Erstellung des entsprechenden AST
    public ASTNode parse(List<Token> ts) {
        assert ts != null : "Token list should not be null";

        this.tokens = ts;
        this.currentTokenIndex = 0;
        return parseAdditionSubtraction();
    }

    // Methode zur Analyse von Additionen und Subtraktionen
    private ASTNode parseAdditionSubtraction() {
        // Analysiere Multiplikationen und Divisionen zuerst
        ASTNode left = parseMultiplicationDivision();

        // Analysiere nachfolgende Additionen und Subtraktionen
        while (currentTokenIndex < tokens.size()) {
            Token token = tokens.get(currentTokenIndex);
            // Invariante: Der Token sollte nicht null sein
            assert token != null : "Token should not be null";

            if (token.type.equals("SPECIAL") && (token.value.equals("+") || token.value.equals("-"))) {
                currentTokenIndex++;
                ASTNode right = parseMultiplicationDivision();
                left = new BinaryExpressionNode(token.value, left, right);
            } else {
                break;
            }
        }

        return left;
    }

    // Methode zur Analyse von Multiplikationen und Divisionen
    private ASTNode parseMultiplicationDivision() {
        // Analysiere Potenzierungen zuerst
        ASTNode left = parsePower();

        // Analysiere nachfolgende Multiplikationen und Divisionen
        while (currentTokenIndex < tokens.size()) {
            Token token = tokens.get(currentTokenIndex);
            // Invariante: Der Token sollte nicht null sein
            assert token != null : "Token should not be null";

            if (token.type.equals("SPECIAL") && (token.value.equals("*") || token.value.equals("/"))) {
                currentTokenIndex++;
                ASTNode right = parsePower();
                left = new BinaryExpressionNode(token.value, left, right);
            } else {
                break;
            }
        }

        return left;
    }

    // Methode zur Analyse von Potenzierungen
    private ASTNode parsePower() {
        // Analysiere Werte (Zahlen, Variablen oder geschachtelte Ausdrücke) zuerst
        ASTNode left = parseValue();

        // Analysiere nachfolgende Potenzierungen
        while (currentTokenIndex < tokens.size()) {
            Token token = tokens.get(currentTokenIndex);
            // Invariante: Der Token sollte nicht null sein
            assert token != null : "Token should not be null";

            if (token.type.equals("SPECIAL") && token.value.equals("^")) {
                currentTokenIndex++;
                ASTNode right = parseValue();
                left = new BinaryExpressionNode(token.value, left, right);
            } else {
                break;
            }
        }

        return left;
    }

    // Methode zur Analyse von Werten (Zahlen, Variablen oder geschachtelte Ausdrücke)
    private ASTNode parseValue() {
        Token currentToken = tokens.get(currentTokenIndex);
        // Invariante: Der Token sollte nicht null sein
        assert currentToken != null : "Token should not be null";

        if (currentToken.type.equals("NUMBER")) {
            // Wenn der Token eine Zahl ist, erzeuge einen NumberNode
            currentTokenIndex++;
            return new NumberNode(Double.parseDouble(currentToken.value));
        } else if (currentToken.type.equals("VARIABLE")) {
            // Wenn der Token eine Variable ist, erzeuge einen VariableNode
            currentTokenIndex++;
            return new VariableNode(currentToken.value);
        } else if (currentToken.type.equals("SPECIAL") && currentToken.value.equals("(")) {
            // Wenn der Token eine öffnende Klammer ist, analysiere den darin geschachtelten Ausdruck
            currentTokenIndex++;
            ASTNode expression = parseAdditionSubtraction();
            if (tokens.get(currentTokenIndex).type.equals("SPECIAL") && tokens.get(currentTokenIndex).value.equals(")")) {
                currentTokenIndex++;
                return expression;
            } else {
                throw new IllegalArgumentException("Mismatched parentheses");
            }
        } else {
            throw new IllegalArgumentException("Invalid expression");
        }
    }
}
