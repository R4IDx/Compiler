package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Dateipfad direkt festlegen
        String filePath = "C:\\Users\\Micha\\OneDrive - reutlingen-university.de\\Dokumente\\Studium\\Info3Nr3\\Compiler\\src\\main\\Formel.txt";

        // Initialisierung der Benutzeroberfläche für die mathematische Ausdrucksauswertung
        MathExpressionEvaluatorUI ui = new MathExpressionEvaluatorUI();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                waitForKeypress();
                ui.setExpression(line);
                evaluateExpression(line, ui);
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Lesen der Datei: " + e.getMessage());
        }
    }

    private static void evaluateExpression(String expression, MathExpressionEvaluatorUI ui) {
        // Vorbedingung: Der Ausdruck sollte nicht null sein
        assert expression != null : "Expression should not be null";

        // Lexer zum Tokenisieren des Ausdrucks
        Lexer lexer = new Lexer();
        List<Token> tokens = lexer.lex(expression);
        // Nachbedingung: Die Liste der Tokens sollte nicht null sein
        assert tokens != null : "Token list should not be null";

        // Parser zum Erstellen des abstrakten Syntaxbaums (AST)
        Parser parser = new Parser();
        ASTNode ast = parser.parse(tokens);
        // Nachbedingung: Der AST sollte nicht null sein
        assert ast != null : "AST should not be null";

        // Evaluiere für die Auswertung des AST
        Evaluator evaluator = new Evaluator();

        // Benutzeroberfläche konfigurieren und anzeigen
        ui.setEvaluator(evaluator);
        ui.show();
        ui.openNewFrame();

        // Evaluierung für Werte von x von -20 bis 20
        for (double x = -20; x <= 20; x++) {
            double result = evaluator.evaluate(ast, x);
            // Nachbedingung: Das Ergebnis der Evaluierung sollte nicht NaN sein
            assert !Double.isNaN(result) : "Result should not be NaN";
            System.out.println(ast);
            System.out.println("Für x = " + x + ", Ergebnis = " + result);
        }
    }

    private static void waitForKeypress() {
        System.out.println("Drücken Sie Enter, um fortzufahren...");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
