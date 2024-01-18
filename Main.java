import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String filePath = "src/Test.txt";

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
        assert expression != null : "Expression should not be null";


        Lexer lexer = new Lexer();
        List<Token> tokens = lexer.lex(expression);
        assert tokens != null : "Token list should not be null";

        Parser parser = new Parser();
        ASTNode ast = parser.parse(tokens);
        assert ast != null : "AST should not be null";

        Evaluator evaluator = new Evaluator();

        ui.setEvaluator(evaluator);
        ui.show();
        ui.openNewFrame();

        for (double x = -20; x <= 20; x++) {
            double result = evaluator.evaluate(ast, x);
            assert !Double.isNaN(result) : "Result should not be NaN";
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
